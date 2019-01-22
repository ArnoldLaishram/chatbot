package com.docsapp.chatbot

class Const {

    object Database {
        const val MESSAGE_TABLE = "Message"
        const val QUERY_MESSAGE = "SELECT * from $MESSAGE_TABLE"
        const val UNDELIVERED_QUERY_MESSAGE = "SELECT * from $MESSAGE_TABLE where isDelivered = :isDelivered"
        const val DB_NAME = "chatbot.db"
    }

    object Api {
        const val GET = "get"
    }

}