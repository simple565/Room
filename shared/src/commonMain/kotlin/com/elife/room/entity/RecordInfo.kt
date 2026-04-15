package com.elife.room.entity

/**
 * 记录条目实体类
 */
data class RecordInfo(
    val id: Int,
    val roomId: Int,
    val recordId: Long,
    val attribute: RoomAttributeInfo,
    val value: String
)
