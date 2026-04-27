package com.elife.room.entity.ext

import com.elife.room.core.ext.defaultId
import com.elife.room.database.table.Room
import com.elife.room.database.table.RoomAttribute
import com.elife.room.entity.RoomInfo
import kotlin.time.Clock

/**
 * @author Lian
 * @date 2026/4/23
 */
val RoomInfo.room: Room
    get() = Room(
        id = this.id,
        groupId = Int.defaultId,
        name = this.name,
        icon = this.icon,
        supportCreateList = this.supportCreateList,
        supportQuickAdd = this.supportQuickAdd,
        supportAddBySearch = this.supportAddBySearch,
        createTime = Clock.System.now().toEpochMilliseconds(),
        updateTime = Clock.System.now().toEpochMilliseconds(),
    )

val RoomInfo.attributes: List<RoomAttribute>
    get() {
        val updateTime = Clock.System.now().toEpochMilliseconds()
        return this.attributeInfoList.map {
            RoomAttribute(
                id = it.id,
                roomId = this.id,
                type = it.type,
                configJson = globalJson.encodeToString(it),
                sortOrder = it.sortOrder,
                updateTime = updateTime,
            )
        }
    }