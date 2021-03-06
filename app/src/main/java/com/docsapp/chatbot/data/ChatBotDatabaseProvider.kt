package com.docsapp.chatbot.data

import android.arch.persistence.room.Room
import android.content.Context
import com.docsapp.chatbot.Const
import com.docsapp.chatbot.data.db.ChatBotDatabase

object ChatBotDatabaseProvider {

    fun getChatBotDatabase(context: Context): ChatBotDatabase {
        return Room.databaseBuilder(
            context,
            ChatBotDatabase::class.java,
            Const.Database.DB_NAME
        ).build()
    }
}