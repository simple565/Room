package com.elife.room.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elife.room.database.table.Room
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(room: Room): Long

    @Delete(entity = Room::class)
    suspend fun delete(room: Room)

    @Query("SELECT * FROM room")
    fun getRoomList(): Flow<List<Room>>

    @Query("SELECT * FROM room WHERE id = :id")
    suspend fun getRoom(id: Long): Room?

}