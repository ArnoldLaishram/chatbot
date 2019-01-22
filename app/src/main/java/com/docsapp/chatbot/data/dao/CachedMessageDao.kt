package com.docsapp.chatbot.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.docsapp.chatbot.Const
import com.docsapp.chatbot.data.model.Message

@Dao
interface CachedMessageDao {

    @Query(Const.Database.QUERY_MESSAGE)
    fun getMessages(): List<Message>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessage(message: Message): Long

}