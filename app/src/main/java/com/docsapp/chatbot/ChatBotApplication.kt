package com.docsapp.chatbot

import android.app.Application
import android.content.Context

class ChatBotApplication : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: ChatBotApplication? = null

        /**
         * For more - https://gist.github.com/paraya3636/bf8108a75eb49323e56c0c90dd0747e0
         */
        fun getContext(): Context {
            return instance!!.applicationContext
        }
    }
}