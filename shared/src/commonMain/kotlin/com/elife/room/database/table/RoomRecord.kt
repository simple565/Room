package com.elife.room.database.table

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Lian
 * @date 2026/2/22
 */
@Entity(tableName = "record")
data class RoomRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val roomId: Long,
    val attributeId: Long,
    val recordId: Long,
    val value: String
)
