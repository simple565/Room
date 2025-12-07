package com.elife.room.database

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.elife.room.di.getFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlin.jvm.JvmStatic

/**
 * @author Lian
 * @date 2025/12/4
 */
class DbHelper {
    companion object {
        @JvmStatic
        val instance by lazy {
            getFactory().getDatabaseBuilder()
                .setDriver(BundledSQLiteDriver())
                .setQueryCoroutineContext(Dispatchers.IO)
                .build()
        }
    }
}