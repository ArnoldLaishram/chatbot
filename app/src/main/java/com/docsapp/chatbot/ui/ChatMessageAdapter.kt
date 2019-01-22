package com.docsapp.chatbot.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.docsapp.chatbot.R
import com.docsapp.chatbot.data.model.Message
import com.docsapp.chatbot.data.model.MessageType
import kotlinx.android.synthetic.main.item_received.view.*
import kotlinx.android.synthetic.main.item_sent.view.*

class ChatMessageAdapter(
        private var messages: List<Message>
) : RecyclerView.Adapter<ChatMessageAdapter.StaffCardVH>() {


    class StaffCardVH(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffCardVH {
        val view = if (viewType == Message.SEND_MESSAGE_TYPE)
            LayoutInflater.from(parent.context).inflate(R.layout.item_sent, parent, false)
        else
            LayoutInflater.from(parent.context).inflate(R.layout.item_received, parent, false)

        return StaffCardVH(view)
    }

    override fun onBindViewHolder(holder: StaffCardVH, position: Int) {
        val message = messages[position]
        when (message.type) {
            MessageType.Send -> {
                holder.view.txt_message_sent.text = message.message
            }
            MessageType.Receive -> {
                holder.view.txt_message_receive.text = message.message
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        return if (message.type == MessageType.Send) Message.SEND_MESSAGE_TYPE
        else Message.RECEIVE_MESSAGE_TYPE
    }

    override fun getItemCount() = messages.size

    fun setMessages(messages: List<Message>) {
        this.messages = messages
        notifyDataSetChanged()
    }

}