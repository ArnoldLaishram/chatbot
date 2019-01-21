package com.docsapp.chatbot.ui.presenter

import com.docsapp.chatbot.BasePresenter
import com.docsapp.chatbot.BaseView
import com.docsapp.chatbot.model.Message

interface ChatViewContract {

    interface ChatPresenter : BasePresenter {

        fun sendMsg(message: String)

    }

    interface ChatView : BaseView<ChatPresenter> {

        fun onSendMsgSuccess(message: Message)

    }
}