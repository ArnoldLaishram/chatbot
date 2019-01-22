package com.docsapp.chatbot.data.dao

import android.arch.persistence.room.*
import com.docsapp.chatbot.Const
import com.docsapp.chatbot.data.model.Message

@Dao
interface CachedMessageDao {

    @Query(Const.Database.QUERY_MESSAGE)
    fun getMessages(): List<Message>

    @Query(Const.Database.UNDELIVERED_QUERY_MESSAGE)
    fun getUndeliveredMessages(isDelivered: Boolean = false): List<Message>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessage(message: Message): Long

    @Update
    fun updateMessage(message: Message)

}