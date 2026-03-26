package com.elife.room.database.table

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Lian
 * @date 2026/2/22
 */
@Entity(tableName = "room_attribute")
data class RoomAttribute(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val roomId: Int,
    val type: String,
    val configJson: String,
    val sortOrder: Int,
    val updateTime: Long
)