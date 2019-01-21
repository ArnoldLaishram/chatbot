package com.docsapp.chatbot

import com.docsapp.chatbot.model.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ChatBotService {

    @GET("get")
    fun sendMessage(@Query("message") message: String): retrofit2.Call<Response>

}