package com.docsapp.chatbot.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.docsapp.chatbot.NetworkUtil

class NetworkChangeReceiver(private val conectivityListener: ConnectivityListener) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        conectivityListener.onNetworkConnectionChanged(NetworkUtil.isOnline(context))
    }

    interface ConnectivityListener {
        fun onNetworkConnectionChanged(isOnline: Boolean)
    }
}