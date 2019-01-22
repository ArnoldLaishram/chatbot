package com.docsapp.chatbot.ui.presenter

import com.docsapp.chatbot.BasePresenter
import com.docsapp.chatbot.BaseView
import com.docsapp.chatbot.data.model.Message

interface ChatViewContract {

    interface ChatPresenter : BasePresenter {

        fun getMessages()

        fun sendMsg(message: String)

    }

    interface ChatView : BaseView<ChatPresenter> {

        fun onGetMessages(messageList: MutableList<Message>)

        fun onSendMsgStarted(message: Message)

        fun onSendMsgSuccess(message: Message)

    }
}