package com.elife.room.di

import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import com.elife.room.MyApplication
import com.elife.room.database.AppDatabase

class Factory(): IFactory {
    override fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
        Log.d("DemoApp", "getDatabaseBuilder: ")
        val context = MyApplication.application.applicationContext
        val dbFile = context.getDatabasePath(AppDatabase.DB_FILE_NAME)
        return Room.databaseBuilder<AppDatabase>(context = context, name = dbFile.absolutePath,)

    }
}

actual fun getFactory(): IFactory {
    return Factory()
}