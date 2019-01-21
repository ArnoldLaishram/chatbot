package com.docsapp.chatbot.model

import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("message")
    val message: String,
    @Transient
    val type: MessageType? = null
) {

    companion object {
        const val SEND_MESSAGE_TYPE = 1
        const val RECEIVE_MESSAGE_TYPE = 2
    }

}

enum class MessageType {
    Send, Receive
}