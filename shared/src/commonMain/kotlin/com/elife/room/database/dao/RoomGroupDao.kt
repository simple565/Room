package com.elife.room.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elife.room.database.table.RoomGroup
import kotlinx.coroutines.flow.Flow

/**
 * @author Lian
 * @date 2026/2/22
 */
@Dao
interface RoomGroupDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(roomGroup: RoomGroup)

    @Delete(entity = RoomGroup::class)
    fun delete(roomGroup: RoomGroup)

    @Query("SELECT * FROM room_group")
    fun loadAll(): Flow<List<RoomGroup>>
}