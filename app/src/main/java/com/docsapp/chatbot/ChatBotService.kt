package com.docsapp.chatbot

import com.docsapp.chatbot.data.model.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ChatBotService {

    @GET(Const.Api.GET)
    fun sendMessage(@Query("message") message: String): retrofit2.Call<Response>

}