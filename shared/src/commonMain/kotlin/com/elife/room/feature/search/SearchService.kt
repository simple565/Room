package com.elife.room.feature.search

import com.elife.room.entity.SearchPluginInfo
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.request
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpMethod
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class SearchService {
    companion object {
        val userAgentHeader = mapOf(
            "User-Agent" to "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3"
        )
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

    suspend fun search(plugin: SearchPluginInfo, query: String, page: Int = 1): List<Map<String, String>> {
        if (plugin.itemRegex.isBlank()) {
            print("item regex is blank")
            return emptyList()
        }
        return try {
            val response = client.request(plugin.url) {
                method = HttpMethod(plugin.method.uppercase())
                url {
                    plugin.params.forEach {
                        parameters.append(it.key, it.value.ifBlank { query } )
                    }
                }
            }
            println(response.bodyAsText())
            val itemMatches = Regex(plugin.itemRegex).findAll(response.bodyAsText())
            return itemMatches.map { match ->
                val content = match.groupValues[1]
                return@map plugin.mapping.associate {
                    it.name to extractField(content, it.regex)
                }
            }.toList().also { println(it) }
        } catch (e: Exception) {
            println("error:" + e.message)
            emptyList()
        }
    }

    /**
     * 从JSON片段中提取指定字段的值
     */
    private fun extractField(jsonContent: String, regex: String): String {
        val regex = Regex(regex)
        val match = regex.find(jsonContent) ?: return ""
        println(match.groupValues)
        return match.groupValues.getOrNull(1) ?: ""
    }

    fun close() {
        client.close()
    }
}
