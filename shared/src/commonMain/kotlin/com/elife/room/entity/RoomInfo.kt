package com.elife.room.entity

import com.elife.room.core.ext.defaultId
import kotlinx.serialization.Serializable

/**
 * 房间信息
 * @author lianml
 * @date 2025/11/5
 */
@Serializable
data class RoomInfo(
    val id: Int = Int.defaultId,
    val name: String = "",
    val icon: String = "",
    val lastUpdateTime: Long = 0L,
    val listColumnCount: Int = 1,
    val supportQuickAdd: Boolean = false,
    val supportCreateList: Boolean = false,
    val showCalendarView: Boolean = false,
    val attributes: List<RoomAttribute> = emptyList()
)

@Serializable
data class RoomBriefInfo(
    val id: Int = Int.defaultId,
    val name: String,
    val icon: String = "",
    val lastUpdateTime: String = "",
    val supportQuickCreate: Boolean = false
)
