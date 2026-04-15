package com.elife.room.entity

import com.elife.room.core.ext.defaultId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 房间 信息
 * @author lianml
 * @date 2025/11/5
 */
@Serializable
data class RoomInfo(
    val id: Long = Long.defaultId,
    val name: String = "",
    val icon: String = "",
    val groupName: String = "",
    val supportAddBySearch: Boolean = false,
    val supportQuickAdd: Boolean = false,
    val supportCreateList: Boolean = false,
    @SerialName("attributes")
    val attributeInfoList: List<RoomAttributeInfo> = emptyList(),
    val createTime: Long = 0L,
    val lastUpdateTime: Long = 0L
)

@Serializable
data class RoomBriefInfo(
    val id: Long = Long.defaultId,
    val name: String,
    val icon: String = "",
    val lastUpdateTime: String = "",
    val supportQuickCreate: Boolean = false
)
