package com.elife.room.database.table

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Lian
 * @date 2026/2/22
 */
@Entity(tableName = "room_record")
data class RoomRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val roomId: Int,
    val attributeId: Int,
    val value: String
)
