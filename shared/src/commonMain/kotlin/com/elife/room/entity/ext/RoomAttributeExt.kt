package com.elife.room.entity.ext

import com.elife.room.database.table.RoomAttribute
import com.elife.room.entity.RoomAttributeInfo
import kotlinx.serialization.json.Json

/**
 * @author Lian
 * @date 2026/4/23
 */
val RoomAttribute.attributeInfo: RoomAttributeInfo
    get() = Json.decodeFromString<RoomAttributeInfo>(this.configJson)
        .withIdAndSortOrder(this.id, this.sortOrder)