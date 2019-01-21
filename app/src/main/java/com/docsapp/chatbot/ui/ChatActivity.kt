package com.docsapp.chatbot.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.docsapp.chatbot.R
import com.docsapp.chatbot.model.Message
import com.docsapp.chatbot.model.MessageType
import com.docsapp.chatbot.ui.presenter.ChatViewContract
import com.docsapp.chatbot.ui.presenter.ChatViewPresenter
import kotlinx.android.synthetic.main.activity_main.*

class ChatActivity : AppCompatActivity(), ChatViewContract.ChatView {

    override lateinit var presenter: ChatViewContract.ChatPresenter

    private lateinit var chatMessageAdapter: ChatMessageAdapter
    private lateinit var recyclerLayoutManager: LinearLayoutManager
    private var messages = mutableListOf<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = ChatViewPresenter(this)

        chatMessageAdapter = ChatMessageAdapter(messages)
        recyclerLayoutManager = LinearLayoutManager(this@ChatActivity)
        recycler_message.apply {
            setHasFixedSize(true)
            adapter = chatMessageAdapter
            layoutManager = recyclerLayoutManager
        }

        fab_send.setOnClickListener {
            // Dont send empty message
            val msg = edt_message.text.toString()
            if (msg.isEmpty()) return@setOnClickListener

            // clear the message editor
            edt_message.text.clear()

            // add the message to the list
            val message = Message(msg, MessageType.Send)
            notifyAdapterWithMessage(message)
            scrollRecyclerToBottom()

            // send message
            presenter.sendMsg(msg)
        }
    }

    private fun scrollRecyclerToBottom() {
        recycler_message.scrollToPosition(messages.size - 1)
    }

    private fun notifyAdapterWithMessage(message: Message) {
        messages.add(message)
        chatMessageAdapter.setMessages(messages)
    }

    override fun onSendMsgSuccess(message: Message) {
        notifyAdapterWithMessage(message)
        scrollRecyclerToBottom()
    }

}
