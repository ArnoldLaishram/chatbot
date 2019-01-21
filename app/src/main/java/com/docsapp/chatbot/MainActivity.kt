package com.docsapp.chatbot

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.docsapp.chatbot.model.Message
import com.docsapp.chatbot.model.MessageType
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var chatMessageAdapter: ChatMessageAdapter
    private var messages = mutableListOf(
            Message("Hi", MessageType.Send),
            Message("Hello", MessageType.Receive))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chatMessageAdapter = ChatMessageAdapter(messages)
        recycler_message.apply {
            setHasFixedSize(true)
            adapter = chatMessageAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }
}
