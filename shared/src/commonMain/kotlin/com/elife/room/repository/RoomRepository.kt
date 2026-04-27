package com.elife.room.repository

import com.elife.room.database.dao.RoomAttributeDao
import com.elife.room.database.dao.RoomDao
import com.elife.room.database.dao.RoomGroupDao
import com.elife.room.database.table.Room
import com.elife.room.database.table.RoomGroup
import com.elife.room.entity.RoomInfo
import com.elife.room.entity.ext.attributeInfo
import com.elife.room.entity.ext.attributes
import com.elife.room.entity.ext.room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @author Lian
 * @date 2026/2/21
 */
class RoomRepository : KoinComponent {
    private val roomDao: RoomDao by inject<RoomDao>()
    private val groupDao: RoomGroupDao by inject<RoomGroupDao>()
    private val attributeDao: RoomAttributeDao by inject<RoomAttributeDao>()
    fun getRoomGroupListFlow(): Flow<List<RoomGroup>> {
        return groupDao.loadAll()
    }

    fun getRoomListFlow(): Flow<List<Room>> {
        return roomDao.getRoomList()
    }

    suspend fun getRoomInfo(id: Long): RoomInfo? {
        return withContext(Dispatchers.IO) {
            val roomDeferred = async { roomDao.getRoom(id) }
            val attributeDeferred = async { attributeDao.getAttributesByRoomId(id) }
            val room = roomDeferred.await() ?: return@withContext null
            val attributes = attributeDeferred.await().map { it.attributeInfo }
            return@withContext RoomInfo(
                id = room.id,
                name = room.name,
                icon = room.icon,
                lastUpdateTime = room.updateTime,
                supportQuickAdd = room.supportQuickAdd,
                supportCreateList = room.supportCreateList,
                attributeInfoList = attributes
            )
        }
    }

    suspend fun addRoom(roomInfo: RoomInfo) {
        val roomId = roomDao.insert(roomInfo.room)
        attributeDao.insert(roomInfo.copy(id = roomId).attributes)
    }

    suspend fun deleteRoom(roomId: Long) {
        withContext(Dispatchers.IO) {
            roomDao.delete(roomId)
            attributeDao.deleteByRoomId(roomId)
        }
    }

}