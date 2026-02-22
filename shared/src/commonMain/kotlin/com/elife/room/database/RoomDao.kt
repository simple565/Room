package com.elife.room.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(room: Room)

    @Query("SELECT * FROM room")
    fun getRoomList(): Flow<List<Room>>

    @Query("SELECT * FROM room WHERE id = :id")
    suspend fun getRoom(id: Int): Room?

}
