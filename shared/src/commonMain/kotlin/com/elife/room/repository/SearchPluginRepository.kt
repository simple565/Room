package com.elife.room.repository

import com.elife.room.database.dao.SearchPluginDao
import com.elife.room.database.table.SearchPlugin
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SearchPluginRepository : KoinComponent {
    private val searchPluginDao: SearchPluginDao by inject<SearchPluginDao>()

    fun getPluginListFlow(): Flow<List<SearchPlugin>> {
        return searchPluginDao.getPluginList()
    }

    suspend fun getPluginDetail(id: Int): SearchPlugin? {
        return searchPluginDao.getPlugin(id)
    }

    suspend fun addPlugin(plugin: SearchPlugin) {
        searchPluginDao.insert(plugin)
    }

    suspend fun deletePlugin(plugin: SearchPlugin) {
        searchPluginDao.delete(plugin)
    }
}
