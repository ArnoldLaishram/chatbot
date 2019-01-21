package com.docsapp.chatbot.ui.presenter

import android.util.Log
import com.docsapp.chatbot.ChatBotServiceProvider
import com.docsapp.chatbot.model.Message
import com.docsapp.chatbot.model.MessageType
import com.docsapp.chatbot.model.Response
import retrofit2.Call
import retrofit2.Callback

class ChatViewPresenter(var view: ChatViewContract.ChatView? = null) : ChatViewContract.ChatPresenter {

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

    override fun sendMsg(message: String) {
        sendMessageCallBack = ChatBotServiceProvider.getService().sendMessage(message)

        sendMessageCallBack!!.enqueue(object : Callback<Response> {
            override fun onFailure(call: Call<Response>, t: Throwable) {
                if (isViewNotVisible() || sendMessageCallBack?.isCanceled!!) return
                Log.d("", "Message not sent")
            }

            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                if (!response.isSuccessful || isViewNotVisible() || sendMessageCallBack?.isCanceled!!) return
                val msgResponse = response.body() ?: return
                view!!.onSendMsgSuccess(Message(msgResponse.args.message, MessageType.Receive))
            }

        })
    }


}