package com.elife.room.di

import com.elife.room.database.AppDatabase
import com.elife.room.database.dao.RecordDao
import com.elife.room.database.dao.RoomAttributeDao
import com.elife.room.database.dao.RoomDao
import com.elife.room.database.dao.RoomGroupDao
import com.elife.room.database.dao.SearchPluginDao
import com.elife.room.feature.detail.RoomDetailViewModel
import com.elife.room.feature.home.EditRoomViewModel
import com.elife.room.feature.home.HomeViewModel
import com.elife.room.feature.record.RecordEditViewModel
import com.elife.room.feature.search.PluginSearchViewModel
import com.elife.room.feature.search.SearchPluginManagerViewModel
import com.elife.room.repository.RecordRepository
import com.elife.room.repository.RoomRepository
import com.elife.room.repository.SearchPluginRepository
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
    single<RecordDao> { get<AppDatabase>().recordDao() }
    single<RoomAttributeDao> { get<AppDatabase>().roomAttributeDao() }
    single<SearchPluginDao> { get<AppDatabase>().searchPluginDao() }

    factory<RoomRepository> { RoomRepository() }
    factory<SearchPluginRepository> { SearchPluginRepository() }
    factory<RecordRepository> { RecordRepository() }

    viewModel { HomeViewModel(repository = get()) }
    viewModel { EditRoomViewModel(repository = get()) }
    viewModel { RoomDetailViewModel(repository = get(), recordRepository = get()) }
    viewModel { RecordEditViewModel(recordRepository = get()) }
    viewModel { SearchPluginManagerViewModel(repository = get()) }
    viewModel { PluginSearchViewModel(repository = get()) }
}

expect fun platformModule(): Module