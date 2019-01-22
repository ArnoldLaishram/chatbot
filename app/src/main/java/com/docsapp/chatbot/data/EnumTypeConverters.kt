package com.docsapp.chatbot.data

import android.arch.persistence.room.TypeConverter
import com.docsapp.chatbot.data.model.MessageType

class EnumTypeConverters {

    companion object {

        @TypeConverter
        @JvmStatic
        fun messageTypeToString(type: MessageType): String = type.name

        @TypeConverter
        @JvmStatic
        fun stringToMessageType(type: String): MessageType = MessageType.valueOf(type)

    }

}