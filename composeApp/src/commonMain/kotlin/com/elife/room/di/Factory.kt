package com.elife.room.di

import androidx.room.RoomDatabase
import com.elife.room.database.AppDatabase


interface IFactory {
    fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase>
}

expect fun getFactory(): IFactory
