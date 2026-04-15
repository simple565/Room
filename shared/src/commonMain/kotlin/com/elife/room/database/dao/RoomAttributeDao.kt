package com.elife.room.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elife.room.database.table.RoomAttribute

@Dao
interface RoomAttributeDao {

    /**
     * 批量插入属性
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(roomAttributes: List<RoomAttribute>)

    @Delete
    fun delete(roomAttribute: RoomAttribute)

    @Query("SELECT * FROM room_attribute WHERE roomId = :roomId ORDER BY sortOrder ASC")
    fun getAttributesByRoomId(roomId: Long): List<RoomAttribute>
}