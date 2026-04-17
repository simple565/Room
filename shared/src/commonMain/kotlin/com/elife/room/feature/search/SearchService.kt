package com.elife.room.feature.search

import com.elife.room.database.table.SearchPlugin
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class SearchService {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun search(plugin: SearchPlugin, query: String, page: Int = 1): List<Map<String, String>> {
        return try {
            val response = client.get(plugin.url) {
                plugin.params.forEach {

                }
            }
            emptyList()
        } catch (e: Exception) {
            throw e
        }
    }

    fun close() {
        client.close()
    }
}
