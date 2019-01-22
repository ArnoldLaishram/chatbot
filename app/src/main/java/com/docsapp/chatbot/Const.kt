package com.docsapp.chatbot

class Const {

    object Database {
        const val MESSAGE_TABLE = "Message"
        const val QUERY_MESSAGE = "SELECT * from $MESSAGE_TABLE"
    }

    object Api {
        const val GET = "get"
    }

}