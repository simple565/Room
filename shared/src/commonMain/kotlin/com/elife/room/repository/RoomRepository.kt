package com.elife.room.repository

import com.elife.room.core.ext.defaultId
import com.elife.room.database.dao.RoomAttributeDao
import com.elife.room.database.dao.RoomDao
import com.elife.room.database.dao.RoomGroupDao
import com.elife.room.database.table.Room
import com.elife.room.database.table.RoomAttribute
import com.elife.room.database.table.RoomGroup
import com.elife.room.entity.RoomAttributeInfo
import com.elife.room.entity.RoomInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.time.Clock

/**
 * @author Lian
 * @date 2026/2/21
 */
class RoomRepository : KoinComponent {
    private val roomDao: RoomDao by inject<RoomDao>()
    private val groupDao: RoomGroupDao by inject<RoomGroupDao>()
    private val attributeDao: RoomAttributeDao by inject<RoomAttributeDao>()
    private val globalJson = Json { encodeDefaults = true }
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
            val attributes = attributeDeferred.await().map { it.toAttributeInfo() }
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

    private val RoomInfo.room: Room
        get() = Room(
            id = this.id,
            groupId = Int.defaultId,
            name = this.name,
            icon = this.icon,
            supportCreateList = this.supportCreateList,
            supportQuickAdd = this.supportQuickAdd,
            supportAddBySearch = this.supportAddBySearch,
            createTime = Clock.System.now().toEpochMilliseconds(),
            updateTime = Clock.System.now().toEpochMilliseconds(),
        )

    private val RoomInfo.attributes: List<RoomAttribute>
        get() {
            val updateTime = Clock.System.now().toEpochMilliseconds()
            return this.attributeInfoList.map {
                RoomAttribute(
                    id = it.id,
                    roomId = this.id,
                    type = it.type,
                    configJson = globalJson.encodeToString(it),
                    sortOrder = it.sortOrder,
                    updateTime = updateTime,
                )
            }
        }

    private fun RoomAttribute.toAttributeInfo(): RoomAttributeInfo {
        return globalJson.decodeFromString<RoomAttributeInfo>(this.configJson)
    }

}