package com.docsapp.chatbot.model

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("args")
    val args: Message
)