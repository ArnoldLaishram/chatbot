package com.docsapp.chatbot.model

data class Message(
        val message: String,
        val type: MessageType) {

    companion object {
        const val SEND_MESSAGE_TYPE = 1
        const val RECEIVE_MESSAGE_TYPE = 2
    }

}

enum class MessageType {
    Send, Receive
}