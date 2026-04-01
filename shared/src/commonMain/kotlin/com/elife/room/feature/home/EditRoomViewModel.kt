package com.elife.room.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elife.room.core.ext.defaultId
import com.elife.room.database.dao.RoomDao
import com.elife.room.database.table.Room
import com.elife.room.entity.RoomInfo
import com.elife.room.entity.RoomTemplate
import com.elife.room.repository.RoomRepository
import com.elife.room.shared.Res
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.random.Random
import kotlin.time.Clock

/**
 * @author lianml
 * @date 2025/11/6
 */
class EditRoomViewModel(private val repository: RoomRepository) : ViewModel() {
    private val _roomInfoState: MutableStateFlow<RoomInfo> = MutableStateFlow(RoomInfo())
    val roomInfo: StateFlow<RoomInfo> = _roomInfoState.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    /*val roomInfoState = combine(
        _roomId.flatMapLatest { roomDao.getRoom(it) },
        _roomId.flatMapLatest { roomDao.getRoom(it) })
    { room, room2 ->
        if (null == room) {
            return@combine RoomInfo()
        }
        return@combine RoomInfo(
            id = room.id,
            name = room.name,
            icon = room.icon,
            lastUpdateTime = room.updateTime,
            supportQuickAdd = room.supportQuickAdd,
            supportCreateList = room.supportCreateList,
            showCalendarView = room.showCalendarView
        )
    }.stateIn(
        scope = viewModelScope,
        initialValue = RoomInfo(),
        started = SharingStarted.WhileSubscribed()
    )
*/
    fun loadRoomInfo(id: Int) = viewModelScope.launch(Dispatchers.IO) {

    }

    fun loadRoomInfo(fileName: String) = viewModelScope.launch(Dispatchers.IO) {
        val roomInfo = Json.decodeFromString<RoomInfo>(
            Res.readBytes("files/template_room/${fileName}").decodeToString()
        )
        _roomInfoState.value = roomInfo
    }

    fun addRoom() = viewModelScope.launch(Dispatchers.IO) {
        val room = Room(
            id = Int.defaultId,
            groupId = Int.defaultId,
            name = "Room ${Random.nextInt(10)}",
            icon = "",
            supportCreateList = false,
            supportQuickAdd = false,
            supportAddBySearch = false,
            createTime = Clock.System.now().toEpochMilliseconds(),
            updateTime = Clock.System.now().toEpochMilliseconds(),
        )
        repository.addRoom(room)
    }
}