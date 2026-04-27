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
    suspend fun insert(roomAttributes: List<RoomAttribute>)

    @Delete
    suspend fun delete(roomAttribute: RoomAttribute)

    @Query("SELECT * FROM room_attribute WHERE roomId = :roomId ORDER BY sortOrder ASC")
    suspend fun getAttributesByRoomId(roomId: Long): List<RoomAttribute>


    @Query("DELETE FROM room_attribute WHERE roomId = :roomId")
    suspend fun deleteByRoomId(roomId: Long)
}