package com.docsapp.chatbot.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.docsapp.chatbot.data.EnumTypeConverters
import com.docsapp.chatbot.data.dao.CachedMessageDao
import com.docsapp.chatbot.data.model.Message

@Database(entities = [Message::class], version = 1, exportSchema = false)
@TypeConverters(EnumTypeConverters::class)
abstract class ChatBotDatabase : RoomDatabase() {

    abstract fun chachedMessageDao(): CachedMessageDao

}