package com.elife.room.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elife.room.entity.RoomBriefInfo
import com.elife.room.repository.RoomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn

/**
 * @author lianml
 * @date 2025/11/6
 */
class HomeViewModel(repository: RoomRepository) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val briefListFlow = repository.getRoomListFlow()
        .flowOn(Dispatchers.IO)
        .mapLatest { roomList ->
            roomList.map {
                RoomBriefInfo(id = it.id, name = it.name)
            }
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = emptyList(),
            started = SharingStarted.WhileSubscribed()
        )
}