package com.elife.room.database.table

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * @author Lian
 * @date 2026/2/22
 */
@Entity(tableName = "room_group", indices = [Index(value = ["name"], unique = true)])
data class RoomGroup(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
)
