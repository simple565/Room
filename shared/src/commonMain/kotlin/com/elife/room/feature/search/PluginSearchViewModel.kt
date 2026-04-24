package com.elife.room.feature.search

import androidx.lifecycle.ViewModel
import com.elife.room.entity.SearchPluginInfo
import com.elife.room.repository.SearchPluginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class PluginSearchViewModel(private val repository: SearchPluginRepository): ViewModel() {


    fun getPluginListFlow(pluginIdList: List<Long>) : Flow<List<SearchPluginInfo>> {
        return repository.getPluginListFlow()
            .flowOn(Dispatchers.IO)
            .map { it.filter { plugin -> plugin.id in pluginIdList } }
    }
}