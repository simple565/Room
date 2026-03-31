package com.elife.room.entity

import kotlinx.serialization.Serializable

/**
 * 房间 模板实体类
 * @date 2026/3/30
 */
@Serializable
data class RoomTemplate(
    val name: String,
    val description: String,
    val file: String
)
