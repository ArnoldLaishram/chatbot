package com.docsapp.chatbot.ui

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.docsapp.chatbot.R
import com.docsapp.chatbot.data.ChatBotDatabaseProvider
import com.docsapp.chatbot.data.model.Message
import com.docsapp.chatbot.receiver.NetworkChangeReceiver
import com.docsapp.chatbot.ui.presenter.ChatViewContract
import com.docsapp.chatbot.ui.presenter.ChatViewPresenter
import kotlinx.android.synthetic.main.activity_main.*

class ChatActivity : AppCompatActivity(), ChatViewContract.ChatView, NetworkChangeReceiver.ConnectivityListener {

    override lateinit var presenter: ChatViewContract.ChatPresenter

    private lateinit var chatMessageAdapter: ChatMessageAdapter
    private lateinit var recyclerLayoutManager: LinearLayoutManager
    private var messages: MutableList<Message> = mutableListOf()
    private lateinit var networkChangeReceiver: NetworkChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        networkChangeReceiver = NetworkChangeReceiver(this)
        presenter = ChatViewPresenter(this, ChatBotDatabaseProvider.getChatBotDatabase(this.applicationContext))

        initViews()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun initViews() {
        chatMessageAdapter = ChatMessageAdapter(messages)
        recyclerLayoutManager = LinearLayoutManager(this@ChatActivity)
        recycler_message.apply {
            setHasFixedSize(true)
            adapter = chatMessageAdapter
            layoutManager = recyclerLayoutManager
        }
        fab_send.setOnClickListener {
            handleSendFabClicked()
        }
    }

    private fun handleSendFabClicked() {
        // Dont send empty message
        val msg = edt_message.text.toString()
        if (msg.isEmpty()) return

        // clear the message editor
        edt_message.text.clear()

        // send message
        presenter.sendMsg(msg)
    }

    override fun onResume() {
        super.onResume()
        presenter.getMessages()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
        unregisterReceiver(networkChangeReceiver)
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

    override fun onSendMsgStarted(message: Message) {
        notifyAdapterWithMessage(message)
        scrollRecyclerToBottom()
    }

    override fun onGetMessages(messageList: MutableList<Message>) {
        this.messages = messageList
        chatMessageAdapter.setMessages(messages)
        scrollRecyclerToBottom()
    }

    override fun onNetworkConnectionChanged(isOnline: Boolean) {
        if (isOnline) sendUndeliveredMessage()
    }

    private fun sendUndeliveredMessage() {

    }

}
