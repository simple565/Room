package com.elife.room.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elife.room.database.DbHelper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.stateIn

/**
 * @author Lian
 * @date 2025/12/4
 */
class RoomDetailViewModel : ViewModel() {

    private val _loadRoomIdState = MutableStateFlow(0)

    @OptIn(ExperimentalCoroutinesApi::class)
    val room = _loadRoomIdState.flatMapLatest {  DbHelper.instance.roomDao().getRoom(it) }
        .onCompletion { print("onCompletion: load room") }
        .stateIn(
            scope = viewModelScope,
            initialValue = null,
            started = SharingStarted.WhileSubscribed()
        )

    fun loadRoom(id: Int) {
        _loadRoomIdState.value = id
    }
}