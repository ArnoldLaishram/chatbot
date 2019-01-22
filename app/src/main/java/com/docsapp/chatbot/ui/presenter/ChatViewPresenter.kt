package com.docsapp.chatbot.ui.presenter

import android.os.Handler
import android.util.Log
import com.docsapp.chatbot.ChatBotApplication
import com.docsapp.chatbot.ChatBotServiceProvider
import com.docsapp.chatbot.NetworkUtil
import com.docsapp.chatbot.ThreadExecutor
import com.docsapp.chatbot.data.db.ChatBotDatabase
import com.docsapp.chatbot.data.model.Message
import com.docsapp.chatbot.data.model.MessageType
import com.docsapp.chatbot.data.model.Response
import retrofit2.Call
import retrofit2.Callback
import java.util.concurrent.Callable

class ChatViewPresenter(
        var view: ChatViewContract.ChatView? = null,
        var chatBotDb: ChatBotDatabase
) : ChatViewContract.ChatPresenter {

    private var sendMessageCallBack: Call<Response>? = null
    private var threadExecutor = ThreadExecutor()

    override fun onDestroy() {
        view = null
        if (sendMessageCallBack != null) {
            sendMessageCallBack!!.cancel()
        }
        threadExecutor.stop()
    }

    override fun isViewNotVisible(): Boolean {
        return view == null
    }

    private fun insertSMS(message: String, type: MessageType, isDelivered: Boolean): Message {
        val sms = Message(id = null, message = message, type = type, isDelivered = isDelivered)
        val msgInserted = chatBotDb.chachedMessageDao().insertMessage(sms)
        sms.id = msgInserted
        return sms
    }

    override fun getMessages() {
        threadExecutor.execute(GetMessagesRunnable())
    }

    override fun resendUnDeliveredMessages() {
        threadExecutor.execute(SendUnDeliveredRunnable())
    }

    override fun sendMsg(message: String) {

        val future = threadExecutor.execute(getSendMsgStartCallable(message))
        val sendSMS = future.get()

        view?.onSendMsgStarted(sendSMS)

        // Its better to not to try if internet is not connected
        if (!NetworkUtil.isOnline(ChatBotApplication.getContext())) return
        sendMessageCallBack = ChatBotServiceProvider.getService().sendMessage(message)

        sendMessageCallBack!!.enqueue(object : Callback<Response> {
            override fun onFailure(call: Call<Response>, t: Throwable) {
                Log.d("", "Message not sent. Ignored. Will be sent when internet connection changes")
            }

            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                if (!response.isSuccessful || isViewNotVisible() || sendMessageCallBack?.isCanceled!!) return
                val msgResponse = response.body() ?: return
                sendSMS.isDelivered = true
                threadExecutor.execute(UpdateMessageDeliveredRunnable(sendSMS))
                threadExecutor.execute(SendMgsSuccessRunnable(msgResponse.args.message))
            }
        })
    }

    private fun resendMessage(message: Message) {
        sendMessageCallBack = ChatBotServiceProvider.getService().sendMessage(message.message)
        sendMessageCallBack!!.enqueue(object : Callback<Response> {
            override fun onFailure(call: Call<Response>, t: Throwable) {
                Log.d("", "Message not sent. Ignored. Will be sent when internet connection changes")
            }

            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                if (!response.isSuccessful || isViewNotVisible() || sendMessageCallBack?.isCanceled!!) return
                val msgResponse = response.body() ?: return
                message.isDelivered = true
                threadExecutor.execute(UpdateMessageDeliveredRunnable(message))
                threadExecutor.execute(SendMgsSuccessRunnable(msgResponse.args.message))
            }
        })
    }

    private fun getSendMsgStartCallable(message: String): Callable<Message> {
        return Callable { insertSMS(message, MessageType.Send, false) }
    }

    inner class SendMgsSuccessRunnable(val message: String) : Runnable {
        private val handler = Handler()
        override fun run() {
            val sendSMS = insertSMS(message, MessageType.Receive, true)
            handler.post {
                view?.onSendMsgSuccess(sendSMS)
            }
        }
    }

    inner class GetMessagesRunnable : Runnable {
        private val handler = Handler()
        override fun run() {
            val msgList = chatBotDb.chachedMessageDao().getMessages().toMutableList()
            handler.post {
                view?.onGetMessages(msgList)
            }
        }

    }

    inner class SendUnDeliveredRunnable : Runnable {
        private val handler = Handler()
        override fun run() {
            val unDeliveredMsgList = chatBotDb.chachedMessageDao().getUndeliveredMessages().toMutableList()
            if (unDeliveredMsgList.size == 0) return
            handler.post {
                unDeliveredMsgList.forEach { resendMessage(it) }
            }
        }
    }

    inner class UpdateMessageDeliveredRunnable(private val message: Message) : Runnable {
        private val handler = Handler()
        override fun run() {
            chatBotDb.chachedMessageDao().updateMessage(message)
            handler.post {
                view?.onResendSuccess(message)
            }
        }
    }

}