package com.elife.room.entity

import kotlinx.serialization.Serializable

/**
 * 房间信息
 * @author lianml
 * @date 2025/11/5
 */
@Serializable
data class RoomInfo(
    val id: Int,
    val name: String,
    val icon: String = "",
    val lastUpdateTime: Long = 0L,
    val listColumnCount: Int = 1,
    val supportQuickCreate: Boolean = false,
    val supportCreateList: Boolean = false,
    val showCalendarView: Boolean = false,
    val statViewTypes: List<String> = listOf()
)

@Serializable
data class RoomBriefInfo(
    val id: Int,
    val name: String,
    val icon: String = "",
    val lastUpdateTime: String = "",
    val supportQuickCreate: Boolean = false
)
