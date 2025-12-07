package com.elife.room

import android.app.Application
import android.util.Log

class MyApplication : Application() {
    companion object Companion {
        private const val TAG = "MyApplication"
        lateinit var application: Application
    }

    override fun onCreate() {
        application = this
        super.onCreate()
        Log.d(TAG, "onCreate: ")
    }
}