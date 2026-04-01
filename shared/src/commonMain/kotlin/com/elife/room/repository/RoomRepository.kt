package com.elife.room.repository

import com.elife.room.database.dao.RoomDao
import com.elife.room.database.dao.RoomGroupDao
import com.elife.room.database.table.Room
import com.elife.room.database.table.RoomGroup
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @author Lian
 * @date 2026/2/21
 */
class RoomRepository : KoinComponent {
    private val roomDao: RoomDao by inject<RoomDao>()

    private val groupDao: RoomGroupDao by inject<RoomGroupDao>()
    fun getRoomGroupListFlow(): Flow<List<RoomGroup>> {
        return groupDao.loadAll()
    }

    fun getRoomListFlow(): Flow<List<Room>> {
        return roomDao.getRoomList()
    }

    suspend fun getRoomDetail(id: Int): Room? {
        return roomDao.getRoom(id)
    }

    suspend fun addRoom(room: Room) {
        roomDao.insert(room)
    }
}