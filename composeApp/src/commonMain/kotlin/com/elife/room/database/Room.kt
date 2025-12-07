package com.elife.room.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "room")
data class Room(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val chartEmoji: String,
    val listColumnCount: Int,
    val supportQuickCreate: Boolean,
    val supportCreateList: Boolean,
    val showCalendarView: Boolean,
    val updateTime: String
)


