package com.elife.room.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elife.room.repository.RecordRepository
import com.elife.room.repository.RoomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Room 详情页 ViewModel
 * @author Lian
 * @date 2025/12/4
 */
class RoomDetailViewModel(
    private val repository: RoomRepository,
    private val recordRepository: RecordRepository
) : ViewModel() {

    private val _loadRoomIdState = MutableStateFlow<Long>(0L)
    private val _selectedTab = MutableStateFlow(RoomDetailTab.RECORDS)
    val selectedTab: StateFlow<RoomDetailTab> = _selectedTab.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val room = _loadRoomIdState.mapLatest { repository.getRoomInfo(it) }
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            initialValue = null,
            started = SharingStarted.WhileSubscribed()
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val recordList = _loadRoomIdState.mapLatest { roomId ->
            if (roomId > 0L) {
                recordRepository.getRecordEntriesByRoomId(roomId)
                    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
                    .value
            } else {
                emptyList()
            }
        }
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            initialValue = emptyList(),
            started = SharingStarted.WhileSubscribed()
        )

    fun loadRoom(id: Long) {
        _loadRoomIdState.value = id
    }

    fun selectTab(tab: RoomDetailTab) {
        _selectedTab.value = tab
    }

    /**
     * 删除记录
     */
    fun deleteRecord(recordId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            // recordRepository.deleteRecord(recordId)
        }
    }
}

/**
 * Room 详情页 Tab 类型
 */
enum class RoomDetailTab {
    /**
     * 记录列表
     */
    RECORDS,

    /**
     * 统计
     */
    STATS
}
