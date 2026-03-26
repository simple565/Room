package com.elife.room.database.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "room")
data class Room(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val groupId: Int,
    val name: String,
    val icon: String,
    val listColumnCount: Int,
    val supportQuickAdd: Boolean,
    val supportCreateList: Boolean,
    val showCalendarView: Boolean,
    val updateTime: Long
)