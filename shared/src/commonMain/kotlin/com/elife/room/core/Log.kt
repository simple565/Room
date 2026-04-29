package com.elife.room.core

import io.github.aakira.napier.Napier

expect fun initLogger()

object Logger {
    init {
        initLogger()
    }

    fun d(tag: String, message: String) {
        Napier.d(tag = tag, message = message)
    }

    fun i(tag: String, message: String) {
        Napier.i(tag = tag, message = message)
    }

    fun w(tag: String, message: String) {
        Napier.w(tag = tag, message = message)
    }

    fun e(tag: String, message: String, throwable: Throwable? = null) {
        Napier.e(tag = tag, message = message, throwable = throwable)
    }
}
