package com.elife.room.repository

import com.elife.room.database.dao.SearchPluginDao
import com.elife.room.database.table.SearchPlugin
import com.elife.room.entity.SearchPluginInfo
import com.elife.room.shared.Res
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SearchPluginRepository : KoinComponent {
    private val searchPluginDao: SearchPluginDao by inject<SearchPluginDao>()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getPluginListFlow(): Flow<List<SearchPluginInfo>> {
        return searchPluginDao.getPluginList().mapLatest { it.map { plugin -> plugin.pluginInfo }}
    }

    suspend fun getPlugin(id: Int): SearchPlugin? {
        return searchPluginDao.getPlugin(id)
    }

    suspend fun addPlugin(pluginInof: SearchPluginInfo) {
        searchPluginDao.insert(pluginInof.plugin)
    }

    suspend fun addPlugins(plugins: List<SearchPluginInfo>) {
        searchPluginDao.insert(plugins.map { it.plugin })
    }

    suspend fun deletePlugin(pluginId: Long) {
        searchPluginDao.delete(pluginId)
    }

    suspend fun loadSearchPluginsFromFiles(): List<SearchPluginInfo> {
        val fileDirPath = "files/search_plugin/"
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
        val files = runCatching {
            Res.readBytes("${fileDirPath}catalog.json").decodeToString().let { json.decodeFromString<List<String>>(it) }
        }.getOrNull() ?: return emptyList()
        return runCatching {
            files.map {
                return@map Res.readBytes("${fileDirPath}$it").decodeToString()
                    .let { string -> json.decodeFromString<SearchPluginInfo>(string) }
            }
        }.getOrNull() ?: emptyList()
    }

    private val SearchPlugin.pluginInfo: SearchPluginInfo
        get() = SearchPluginInfo(
            id = id,
            name = name,
            description = description,
            method = requestMethod,
            url = url,
            headers = Json.decodeFromString(headers),
            params = Json.decodeFromString(params),
            body = Json.decodeFromString(body),
            itemRegex = itemRegex,
            mapping = Json.decodeFromString(mapping),
            enable = enable
        )

    private val SearchPluginInfo.plugin: SearchPlugin
        get() = SearchPlugin(
            id = id,
            name = name,
            description = description,
            requestMethod = method,
            url = url,
            headers = Json.encodeToString(headers),
            params = Json.encodeToString(params),
            body = Json.encodeToString(body),
            itemRegex = itemRegex,
            mapping = Json.encodeToString(mapping),
            enable = enable
        )
}
