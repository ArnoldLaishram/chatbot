package com.docsapp.chatbot

import java.util.concurrent.Executors

object ThreadExecutor {

    private const val THREAD_COUNT = 1
    private val executor = Executors.newFixedThreadPool(THREAD_COUNT)

    fun execute(runnable: Runnable) {
        if (executor.isShutdown || executor.isTerminated) return
        executor.execute(runnable)
    }

    fun stop() {
        if (executor.isShutdown) return
        executor.shutdown()
    }

}
