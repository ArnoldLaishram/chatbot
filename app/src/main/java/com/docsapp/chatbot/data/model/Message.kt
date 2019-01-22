package com.docsapp.chatbot.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.docsapp.chatbot.Const
import com.google.gson.annotations.SerializedName

@Entity(tableName = Const.Database.MESSAGE_TABLE)
class Message(
    @PrimaryKey(autoGenerate = true)
    var id: Long?,
    @SerializedName("message")
    var message: String,
    var type: MessageType? = null
) {

    companion object {
        const val SEND_MESSAGE_TYPE = 1
        const val RECEIVE_MESSAGE_TYPE = 2
    }

}

enum class MessageType {
    Send, Receive
}