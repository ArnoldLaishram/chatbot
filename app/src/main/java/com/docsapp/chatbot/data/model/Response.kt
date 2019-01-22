package com.docsapp.chatbot.data.model

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("args")
    val args: Message
)