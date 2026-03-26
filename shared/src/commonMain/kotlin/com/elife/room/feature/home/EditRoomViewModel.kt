package com.elife.room.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elife.room.core.ext.defaultId
import com.elife.room.database.dao.RoomDao
import com.elife.room.database.table.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.random.Random
import kotlin.time.Clock

/**
 * @author lianml
 * @date 2025/11/6
 */
class EditRoomViewModel : ViewModel(), KoinComponent {
    private val _roomId: MutableStateFlow<Int> = MutableStateFlow(Int.defaultId)
    private val roomDao by inject<RoomDao>()

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
    fun loadRoomConfig(id: Int) {
        _roomId.value = id
    }

    fun addRoom() = viewModelScope.launch(Dispatchers.IO) {
        val room = Room(
            id = Int.defaultId,
            groupId = Int.defaultId,
            name = "Room ${Random.nextInt(10)}",
            icon = "",
            listColumnCount = 3,
            supportCreateList = false,
            supportQuickAdd = false,
            showCalendarView = false,
            updateTime = Clock.System.now().toEpochMilliseconds(),
        )
        roomDao.insert(room)
    }
}