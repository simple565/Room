package com.elife.room

import androidx.room.Room
import androidx.room.RoomDatabase
import com.elife.room.database.AppDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

object IosDatabaseFactory {

    fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
        val dbFilePath = "${documentDirectory()}/${AppDatabase.Companion.DB_FILE_NAME}"
        return Room.databaseBuilder<AppDatabase>(name = dbFilePath)
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun documentDirectory(): String {
        val documentDirectory = NSFileManager.Companion.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        return requireNotNull(documentDirectory?.path)
    }
}