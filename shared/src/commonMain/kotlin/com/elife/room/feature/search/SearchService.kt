package com.elife.room.feature.search

import com.elife.room.core.Logger
import com.elife.room.entity.ResponseMapping
import com.elife.room.entity.SearchParameter
import com.elife.room.entity.SearchPluginInfo
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class SearchService {
    companion object {
        private const val TAG = "SearchService"
    }

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = true
                explicitNulls = false
            })
        }
        install(HttpCache)

        install(HttpTimeout) {
            requestTimeoutMillis = 30_000
            connectTimeoutMillis = 10_000
            socketTimeoutMillis = 10_000
        }
    }

    suspend fun search(plugin: SearchPluginInfo, query: String, page: Int = 1, pageCount: Int = 20): List<List<ResponseMapping>> {
        if (plugin.itemRegex.isBlank()) {
            Logger.e(TAG,"search: item regex is blank")
            return emptyList()
        }
        val offset = (page - 1) * pageCount
        val replacements = mapOf(
            "{keyword}" to query,
            "{page}" to page.toString(),
            "{count}" to pageCount.toString(),
            "{offset}" to offset.toString()
        )
        return try {
            val response = client.request(plugin.url) {
                method = HttpMethod(plugin.method.uppercase())
                Logger.d(TAG, "search: header: ${plugin.headers}")
                plugin.headers.forEach {
                    headers.append(it.key, it.value)
                }
                url {
                    plugin.params.forEach {
                        parameters.append(it.key, computeValue(it, replacements, it.value))
                    }
                }
                if (plugin.body.isNotEmpty()) {
                    contentType(ContentType.Application.Json)
                    val bodyMap = plugin.body.associate {
                        it.key to computeValue(it, replacements, it.value)
                    }
                    setBody(bodyMap)
                }
            }
            val itemMatches = Regex(plugin.itemRegex).findAll(response.bodyAsText())
            return itemMatches.map { match ->
                val content = if (match.groupValues.size > 1) match.groupValues[1] else match.value
                Logger.d(TAG, "search: item: $content")
                return@map plugin.mapping.map {
                    it.copy(value = extractField(content, it.regex))
                }.toList()
            }.toList()
        } catch (e: Exception) {
            Logger.e(TAG, "search: ", e)
            emptyList()
        }
    }

    /**
     * 从JSON片段中提取指定字段的值
     */
    private fun extractField(jsonContent: String, regex: String): String {
        val regex = Regex(regex)
        val match = regex.find(jsonContent) ?: return ""
        return match.groupValues.getOrNull(1) ?: ""
    }

    private fun computeValue(param: SearchParameter, replacements: Map<String, String>, default: String = ""): String {
        val replaced = param.value.replace(Regex("\\{(\\w+)\\}")) { match ->
            replacements[match.groupValues.first()] ?: match.value
        }

        return replaced.ifBlank { default }
    }

    fun close() {
        Logger.d(TAG, "close: ")
        client.close()
    }
}
