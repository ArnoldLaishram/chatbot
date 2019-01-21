package com.docsapp.chatbot

interface BasePresenter {

    fun isViewNotVisible(): Boolean

    fun onDestroy()

}