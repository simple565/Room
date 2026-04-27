package com.elife.room.repository

import com.elife.room.core.ext.defaultId
import com.elife.room.database.dao.RecordDao
import com.elife.room.database.dao.RoomAttributeDao
import com.elife.room.database.table.RoomRecord
import com.elife.room.entity.RecordInfo
import com.elife.room.entity.RoomAttributeInfo
import com.elife.room.entity.ext.attributeInfo
import com.elife.room.entity.ext.globalJson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.time.Clock

/**
 * Room 记录 Repository
 * @author Lian
 * @date 2026/4/4
 */
class RecordRepository : KoinComponent {

    private val attributeDao: RoomAttributeDao by inject()
    private val recordDao: RecordDao by inject()

    /**
     * 获取指定 Room 的所有记录（按 recordId 分组聚合）
     */
    fun getRecordEntriesByRoomId(roomId: Long): Flow<List<RecordInfo>> {
        return emptyFlow()/*recordDao.getRecordsByRoomId(roomId)
            .map { records ->
                records.toRecordEntries()
            }*/
    }

    suspend fun getBlankRecordInfoList(roomId: Long): List<RecordInfo> = withContext(Dispatchers.IO) {
        val recordId = Clock.System.now().toEpochMilliseconds()
        return@withContext attributeDao.getAttributesByRoomId(roomId).map { attribute ->
            RecordInfo(
                id = Long.defaultId,
                roomId = roomId,
                recordId = recordId,
                attribute = globalJson.decodeFromString<RoomAttributeInfo>(attribute.configJson)
                    .withIdAndSortOrder(attribute.id, attribute.sortOrder)
            )
        }
    }

    suspend fun getRecordListById(roomId: Long, recordId: Long): List<RecordInfo> = withContext(Dispatchers.IO) {
        return@withContext recordDao.getRecordAndAttributeList(roomId = roomId, recordId = recordId)
            .map {
                RecordInfo(
                    id = it.record.id,
                    roomId = it.record.roomId,
                    recordId = it.record.recordId,
                    attribute = it.attribute.attributeInfo,
                    value = it.record.value
                )
            }
    }

    suspend fun saveRecordInfo(recordInfoList: List<RecordInfo>) = withContext(Dispatchers.IO) {
        val recordList = recordInfoList.map {
            RoomRecord(
                id = it.id,
                roomId = it.roomId,
                recordId = it.recordId,
                attributeId = it.attribute.id,
                value = it.value
            )
        }
        return@withContext recordDao.insertAll(recordList)
    }
}
