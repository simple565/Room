package com.elife.room.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elife.room.database.table.SearchPlugin
import com.elife.room.repository.SearchPluginRepository
import kotlinx.coroutines.launch

class SearchPluginManagerViewModel(private val repository: SearchPluginRepository) : ViewModel() {
    val pluginList = repository.getPluginListFlow()

    fun savePlugin(plugin: SearchPlugin) = viewModelScope.launch {
       repository.addPlugin(plugin)
    }
}