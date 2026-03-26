package com.elife.room.di

import com.elife.room.database.AppDatabase
import com.elife.room.database.dao.RoomDao
import com.elife.room.database.dao.RoomGroupDao
import com.elife.room.feature.home.HomeViewModel
import com.elife.room.repository.RoomRepository
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        appDeclaration()
        modules(
            commonModule() + platformModule()
        )
    }
}

fun commonModule() = module {
    single<RoomDao> { get<AppDatabase>().roomDao() }
    single<RoomGroupDao> { get<AppDatabase>().roomGroupDao() }
    factory<RoomRepository> { RoomRepository() }
    viewModel { HomeViewModel(repository = get()) }
}

expect fun platformModule(): Module