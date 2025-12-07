package com.elife.room.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.elife.room.database.AppDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

class Factory : IFactory {
    override fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
        val dbFilePath = "${documentDirectory()}/${AppDatabase.DB_FILE_NAME}"
        return Room.databaseBuilder<AppDatabase>(name = dbFilePath)
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun documentDirectory(): String {
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        return requireNotNull(documentDirectory?.path)
    }
}

actual fun getFactory(): IFactory {
    return Factory()
}