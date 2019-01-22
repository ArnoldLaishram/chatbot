package com.docsapp.chatbot

import com.docsapp.chatbot.data.model.Message
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future

const val THREAD_COUNT = 1

class ThreadExecutor {

    private val executor = Executors.newFixedThreadPool(THREAD_COUNT)

    fun execute(runnable: Runnable) {
        if (executor.isShutdown || executor.isTerminated) return
        executor.execute(runnable)
    }

    // Use this when you need value to be returned
    fun execute(callable: Callable<Message>): Future<Message> {
        return executor.submit(callable)
    }

    fun stop() {
        if (executor.isShutdown) return
        executor.shutdown()
    }

}
