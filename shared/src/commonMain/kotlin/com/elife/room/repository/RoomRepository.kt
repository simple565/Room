package com.elife.room.repository

import com.elife.room.database.Room
import com.elife.room.database.RoomDao
import kotlinx.coroutines.flow.Flow

/**
 * @author Lian
 * @date 2026/2/21
 */
class RoomRepository(private val roomDao: RoomDao) {
    fun getRoomListFlow(): Flow<List<Room>> {
        return roomDao.getRoomList()
    }

    suspend fun getRoomDetail(id: Int): Room? {
        return roomDao.getRoom(id)
    }
}