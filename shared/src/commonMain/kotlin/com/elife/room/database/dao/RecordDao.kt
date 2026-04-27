package com.elife.room.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.elife.room.database.table.RecordAndAttribute
import com.elife.room.database.table.RoomRecord
import kotlinx.coroutines.flow.Flow

/**
 * Room 记录 DAO
 * @author Lian
 * @date 2026/4/4
 */
@Dao
interface RecordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: RoomRecord)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(records: List<RoomRecord>): List<Long>

    @Update
    suspend fun update(record: RoomRecord)

    @Delete
    suspend fun delete(record: RoomRecord)

    @Query("DELETE FROM record WHERE recordId = :recordId")
    suspend fun deleteByRecordId(recordId: Long)

    @Query("SELECT * FROM record WHERE roomId = :roomId ORDER BY recordId DESC")
    fun getRecordsByRoomId(roomId: Int): Flow<List<RoomRecord>>

    @Query("SELECT * FROM record WHERE recordId = :recordId")
    suspend fun getRecordsByRecordId(recordId: Long): List<RoomRecord>

    @Query("SELECT DISTINCT recordId FROM record WHERE roomId = :roomId ORDER BY recordId DESC")
    fun getRecordIdsByRoomId(roomId: Int): Flow<List<Long>>

    @Query("SELECT * FROM record WHERE roomId = :roomId AND attributeId = :attributeId")
    fun getRecordsByAttribute(roomId: Int, attributeId: Int): Flow<List<RoomRecord>>

    @Query("SELECT * FROM record WHERE roomId = :roomId AND recordId = :recordId")
    fun getRecordAndAttributeList(roomId: Long, recordId: Long): List<RecordAndAttribute>
}
