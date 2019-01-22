package com.docsapp.chatbot.ui.presenter

import android.os.Handler
import android.util.Log
import com.docsapp.chatbot.ChatBotServiceProvider
import com.docsapp.chatbot.data.db.ChatBotDatabase
import com.docsapp.chatbot.data.model.Message
import com.docsapp.chatbot.data.model.MessageType
import com.docsapp.chatbot.data.model.Response
import retrofit2.Call
import retrofit2.Callback

class ChatViewPresenter(
    var view: ChatViewContract.ChatView? = null,
    var chatBotDb: ChatBotDatabase
) : ChatViewContract.ChatPresenter {

    private var sendMessageCallBack: Call<Response>? = null

    override fun onDestroy() {
        view = null
        if (sendMessageCallBack != null) {
            sendMessageCallBack!!.cancel()
        }
    }

    override fun isViewNotVisible(): Boolean {
        return view == null
    }

    private fun insertSMS(message: String, type: MessageType): Message {
        val sms = Message(id = null, message = message, type = type)
        val msgInserted = chatBotDb.chachedMessageDao().insertMessage(sms)
        sms.id = msgInserted
        return sms
    }

    override fun getMessages() {
        Thread(GetMessagesRunnable()).start()
    }

    override fun sendMsg(message: String) {

        Thread(SendMgsStartedRunnable(message)).start()

        sendMessageCallBack = ChatBotServiceProvider.getService().sendMessage(message)

        sendMessageCallBack!!.enqueue(object : Callback<Response> {
            override fun onFailure(call: Call<Response>, t: Throwable) {
                if (isViewNotVisible() || sendMessageCallBack?.isCanceled!!) return
                Log.d("", "Message not sent")
            }

            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                if (!response.isSuccessful || isViewNotVisible() || sendMessageCallBack?.isCanceled!!) return
                val msgResponse = response.body() ?: return
                Thread(SendMgsSuccessRunnable(msgResponse.args.message)).start()
            }

        })
    }

    inner class SendMgsStartedRunnable(val message: String) : Runnable {
        private val handler = Handler()
        override fun run() {
            val sendSMS = insertSMS(message, MessageType.Send)
            handler.post {
                view?.onSendMsgStarted(sendSMS)
            }
        }
    }

    inner class SendMgsSuccessRunnable(val message: String) : Runnable {
        private val handler = Handler()

        override fun run() {
            val sendSMS = insertSMS(message, MessageType.Receive)
            handler.post {
                view?.onSendMsgSuccess(sendSMS)
            }
        }
    }

    inner class GetMessagesRunnable() : Runnable {

        private val handler = Handler()

        override fun run() {
            val msgList = chatBotDb.chachedMessageDao().getMessages().toMutableList()
            handler.post {
                view?.onGetMessages(msgList)
            }
        }

    }

}