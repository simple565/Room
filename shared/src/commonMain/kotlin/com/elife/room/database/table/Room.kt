package com.elife.room.database.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "room")
data class Room(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val groupId: Int,
    val name: String,
    val icon: String,
    val supportAddBySearch: Boolean,
    val supportCreateList: Boolean,
    val supportQuickAdd: Boolean,
    val createTime: Long,
    val updateTime: Long
)