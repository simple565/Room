package com.elife.room.entity

import com.elife.room.core.ext.defaultId
import io.ktor.http.HttpMethod
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @author Lian
 * @date 2026/4/18
 */
@Serializable
data class SearchPluginInfo(
    val id: Long = Long.defaultId,
    val name: String = "",
    val description: String = "",
    val method: String = HttpMethod.Get.value,
    val url: String = "",
    val headers: Map<String, String> = emptyMap(),
    val body: List<SearchParameter> = emptyList(),
    val params: List<SearchParameter> = emptyList(),
    val itemRegex: String = "",
    val mapping: List<ResponseMapping> = emptyList(),
    @Transient
    val enable: Boolean = true
)

@Serializable
data class SearchParameter(
    val key: String,
    val value: String = ""
)


@Serializable
data class ResponseMapping(
    val type: String,
    val name: String,
    val regex: String,
    val value: String
)