package com.elife.room.di

import com.elife.room.database.AppDatabase
import com.elife.room.database.DatabaseFactory
import com.elife.room.AndroidDatabaseFactory
import org.koin.dsl.module

actual fun platformModule() = module {
    single<AppDatabase> {
        DatabaseFactory.createDatabase(
            AndroidDatabaseFactory.getDatabaseBuilder(context = get())
        )
    }
}