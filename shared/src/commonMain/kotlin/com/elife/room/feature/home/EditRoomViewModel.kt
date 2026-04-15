package com.elife.room.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elife.room.entity.RoomInfo
import com.elife.room.repository.RoomRepository
import com.elife.room.shared.Res
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

/**
 * @author lianml
 * @date 2025/11/6
 */
class EditRoomViewModel(private val repository: RoomRepository) : ViewModel() {
    private val _roomInfoState: MutableStateFlow<RoomInfo> = MutableStateFlow(RoomInfo())
    val roomInfo: StateFlow<RoomInfo> = _roomInfoState.asStateFlow()
    fun loadRoomInfo(id: Long) = viewModelScope.launch(Dispatchers.IO) {
        _roomInfoState.value = RoomInfo()
        val roomInfo = repository.getRoomInfo(id) ?: RoomInfo()
        _roomInfoState.value = roomInfo
    }

    fun loadRoomInfo(fileName: String) = viewModelScope.launch(Dispatchers.IO) {
        _roomInfoState.value = RoomInfo()
        val roomInfo = Json.decodeFromString<RoomInfo>(
            Res.readBytes("files/template_room/${fileName}").decodeToString()
        )
        _roomInfoState.value = roomInfo
    }

    fun saveRoom(roomInfo: RoomInfo) = viewModelScope.launch(Dispatchers.IO) {
        repository.addRoom(roomInfo)
    }

    fun updateRoom(roomInfo: RoomInfo) = viewModelScope.launch(Dispatchers.IO) {

    }
}