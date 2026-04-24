package com.elife.room.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elife.room.core.entity.DataLoadState
import com.elife.room.entity.SearchPluginInfo
import com.elife.room.repository.SearchPluginRepository
import com.elife.room.shared.Res
import com.elife.room.shared.search_plugin_preset_loading
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString

class SearchPluginManagerViewModel(private val repository: SearchPluginRepository) : ViewModel() {

    private val _loadState = MutableStateFlow(Pair(false, ""))

    val pluginList = combine(
        repository.getPluginListFlow(),
        _loadState
    ) { pluginList, state ->
        DataLoadState(isLoading = state.first, msg = state.second, dataList = pluginList)
    }.onStart { emit(DataLoadState(isLoading = true)) }
        .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = DataLoadState()
    )

    fun loadPresetPlugins() = viewModelScope.launch {
        _loadState.update { true to getString(Res.string.search_plugin_preset_loading) }
        val pluginList = repository.loadSearchPluginsFromFiles()
        repository.addPlugins(pluginList)
        _loadState.update { false to "" }
    }

    fun savePlugin(pluginInfo: SearchPluginInfo) = viewModelScope.launch {
       repository.addPlugin(pluginInfo)
    }

    fun deletePlugin(pluginId: Long) = viewModelScope.launch {
        repository.deletePlugin(pluginId)
    }
}