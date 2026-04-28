package com.elife.room.feature.search

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

    suspend fun search(plugin: SearchPluginInfo, query: String, page: Int = 1, pageCount: Int = 10): List<List<ResponseMapping>> {
        if (plugin.itemRegex.isBlank()) {
            print("item regex is blank")
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
                    println(bodyMap)
                    setBody(bodyMap)
                }
            }
            val itemMatches = Regex(plugin.itemRegex).findAll(response.bodyAsText())
            return itemMatches.map { match ->
                // 如果有捕获组则使用捕获组，否则使用整个匹配
                val content = if (match.groupValues.size > 1) match.groupValues[1] else match.value
                println("item: $content")
                return@map plugin.mapping.map {
                    it.copy(value = extractField(content, it.regex))
                }.toList()
            }.toList()
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
        return match.groupValues.getOrNull(1) ?: ""
    }

    private fun computeValue(param: SearchParameter, replacements: Map<String, String>, default: String = ""): String {
        val replaced = param.value.replace(Regex("\\{(\\w+)\\}")) { match ->
            replacements[match.groupValues.first()] ?: match.value
        }

        return replaced.ifBlank { default }
    }

    fun close() {
        client.close()
    }
}
