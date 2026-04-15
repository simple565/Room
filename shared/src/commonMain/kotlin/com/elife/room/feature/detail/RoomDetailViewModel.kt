package com.elife.room.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elife.room.entity.RecordInfo
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

    @OptIn(ExperimentalCoroutinesApi::class)
    val room = _loadRoomIdState.mapLatest { repository.getRoomInfo(it) }
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            initialValue = null,
            started = SharingStarted.WhileSubscribed()
        )

    // 记录列表
    val recordEntries: StateFlow<List<RecordInfo>> = _loadRoomIdState
        .mapLatest { roomId ->
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

    // 当前选中的 Tab（记录列表/统计）
    private val _selectedTab = MutableStateFlow(RoomDetailTab.RECORDS)
    val selectedTab: StateFlow<RoomDetailTab> = _selectedTab.asStateFlow()

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
            recordRepository.deleteRecord(recordId)
        }
    }
}

/**
 * Room 详情页 Tab 类型
 */
enum class RoomDetailTab {
    RECORDS,    // 记录列表
    STATS       // 统计页面
}
