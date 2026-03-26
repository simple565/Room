package com.elife.room.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elife.room.repository.RoomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn

/**
 * @author Lian
 * @date 2025/12/4
 */
class RoomDetailViewModel(repository: RoomRepository) : ViewModel() {

    private val _loadRoomIdState = MutableStateFlow(0)

    @OptIn(ExperimentalCoroutinesApi::class)
    val room = _loadRoomIdState.mapLatest {  repository.getRoomDetail(it) }
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            initialValue = null,
            started = SharingStarted.WhileSubscribed()
        )

    fun loadRoom(id: Int) {
        _loadRoomIdState.value = id
    }
}