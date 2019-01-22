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
    var type: MessageType? = null,
    // will be null for received message type
    var isDelivered: Boolean? = null
) {

    companion object {
        const val SEND_MESSAGE_TYPE = 1
        const val RECEIVE_MESSAGE_TYPE = 2
    }

    fun getIndexOfMessage(messages: List<Message>): Int {
        for(msgIndex in messages.indices) {
            if(messages[msgIndex].id == this.id) return msgIndex
        }
        return -1
    }

}

enum class MessageType {
    Send, Receive
}