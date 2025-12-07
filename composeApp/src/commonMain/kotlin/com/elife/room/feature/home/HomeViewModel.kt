package com.elife.room.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elife.room.database.DbHelper
import com.elife.room.database.Room
import com.elife.room.entity.RoomBriefInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 * @author lianml
 * @date 2025/11/6
 */
class HomeViewModel : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val briefListFlow = DbHelper.instance.roomDao().getRoomList()
        .mapLatest { roomList ->
            roomList.map {
                RoomBriefInfo(id = it.id, name = it.name)
            }
        }
        .onCompletion { print("onCompletion: load room list") }
        .stateIn(
            scope = viewModelScope,
            initialValue = emptyList(),
            started = SharingStarted.WhileSubscribed()
        )

    fun addRoom() = viewModelScope.launch(Dispatchers.IO) {
        val room = Room(
            name = "Room ${Random.nextInt(10)}",
            chartEmoji = "",
            listColumnCount = 3,
            supportCreateList = false,
            supportQuickCreate = false,
            showCalendarView = false,
            updateTime = "",
        )
        DbHelper.instance.roomDao().insert(room)
    }
}