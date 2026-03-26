package com.elife.room.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elife.room.core.ext.defaultId
import com.elife.room.database.table.RoomGroup
import com.elife.room.entity.RoomBriefInfo
import com.elife.room.repository.RoomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn

/**
 * @author lianml
 * @date 2025/11/6
 */
class HomeViewModel(repository: RoomRepository) : ViewModel() {
    private val _selectedTabIndex = MutableStateFlow(0)
    private val _roomListDisplayColumnCount = MutableStateFlow(0)

    val groupTabsState = combine(_selectedTabIndex, repository.getRoomGroupListFlow()) { index, groupList ->
        RoomTabState(
            selectedIndex = index,
            groupList = groupList.ifEmpty { listOf(RoomGroup(id = Int.defaultId, "全部")) }
        )
    }.flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            initialValue = RoomTabState(),
            started = SharingStarted.WhileSubscribed()
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val briefListFlow = combine(
        _roomListDisplayColumnCount,
        repository.getRoomListFlow().mapLatest { roomList -> roomList.map { RoomBriefInfo(id = it.id, name = it.name) } }
    ) { count, list ->
        DisplayRoomList(count, list)
    }.flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            initialValue = DisplayRoomList(),
            started = SharingStarted.WhileSubscribed()
        )

    fun selectGroupTab(index: Int) {
        _selectedTabIndex.value = index
    }

    fun updateRoomListDisplayConfig(columnCount: Int) {
        _roomListDisplayColumnCount.value = columnCount
    }
}

data class RoomTabState(
    val selectedIndex: Int = 0,
    val groupList: List<RoomGroup> = emptyList()
)

data class DisplayRoomList(
    val columnCount: Int = 2,
    val roomList: List<RoomBriefInfo> = emptyList()
)