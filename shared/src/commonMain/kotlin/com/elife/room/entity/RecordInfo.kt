package com.elife.room.entity

import com.elife.room.core.ext.defaultId

/**
 * 记录条目实体类
 */
data class RecordInfo(
    val id: Long = Long.defaultId,
    val roomId : Long = Long.defaultId,
    val recordId: Long = Long.defaultId,
    val attribute: RoomAttributeInfo,
    val value: String = ""
)
