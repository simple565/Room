package com.elife.room.feature.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import com.elife.room.core.ui.theme.spacing
import com.elife.room.entity.RecordInfo
import com.elife.room.entity.RoomAttributeInfo
import com.elife.room.feature.record.RecordEditPage
import com.elife.room.feature.stats.RoomStatsPage
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

/**
 * Room 详情页路由
 */
@Serializable
data class RoomDetailRoute(val id: Long) : NavKey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomDetailPage(
    id: Long,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RoomDetailViewModel = koinViewModel()
) {
    LaunchedEffect(id) {
        viewModel.loadRoom(id)
    }

    val room by viewModel.room.collectAsStateWithLifecycle()
    val recordEntries by viewModel.recordEntries.collectAsStateWithLifecycle()
    val selectedTab by viewModel.selectedTab.collectAsStateWithLifecycle()

    // 编辑/创建记录弹窗状态
    var showEditDialog by remember { mutableStateOf(false) }
    var editingRecordId by remember { mutableStateOf<Long?>(null) }

    // 解析 Room 属性
    val attributes = emptyList<RoomAttributeInfo>()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = room?.name ?: "") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "返回"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            if (selectedTab == RoomDetailTab.RECORDS) {
                FloatingActionButton(
                    onClick = {
                        editingRecordId = null
                        showEditDialog = true
                    },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "添加记录"
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // Tab 切换
            PrimaryTabRow(
                selectedTabIndex = selectedTab.ordinal,
                modifier = Modifier.fillMaxWidth()
            ) {
                Tab(
                    selected = selectedTab == RoomDetailTab.RECORDS,
                    onClick = { viewModel.selectTab(RoomDetailTab.RECORDS) },
                    text = { Text("记录") },
                    icon = { Icon(Icons.Default.List, contentDescription = null) }
                )
                Tab(
                    selected = selectedTab == RoomDetailTab.STATS,
                    onClick = { viewModel.selectTab(RoomDetailTab.STATS) },
                    text = { Text("统计") },
                    icon = { Icon(Icons.Default.Analytics, contentDescription = null) }
                )
            }

            // 内容区域
            when (selectedTab) {
                RoomDetailTab.RECORDS -> {
                    RecordListContent(
                        recordEntries = recordEntries,
                        attributes = attributes,
                        onRecordClick = { recordId ->
                            editingRecordId = recordId
                            showEditDialog = true
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
                RoomDetailTab.STATS -> {
                    if (room != null) {
                        RoomStatsPage(
                            roomId = room!!.id,
                            attributes = attributes,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }

    // 编辑/创建记录弹窗
    if (showEditDialog && room != null) {
        RecordEditPage(
            roomId = room!!.id,
            attributes = attributes,
            recordId = editingRecordId,
            onBackClick = { showEditDialog = false },
            onSaveSuccess = { showEditDialog = false }
        )
    }
}

/**
 * 记录列表内容
 */
@Composable
private fun RecordListContent(
    recordEntries: List<RecordInfo>,
    attributes: List<RoomAttributeInfo>,
    onRecordClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    if (recordEntries.isEmpty()) {
        // 空状态
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "暂无记录",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "点击右下角按钮添加新记录",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(MaterialTheme.spacing.medium),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
        ) {
            items(recordEntries, key = { it.recordId }) { entry ->
                RecordEntryCard(
                    entry = entry,
                    attributes = attributes,
                    onClick = { onRecordClick(entry.recordId) }
                )
            }
        }
    }
}

/**
 * 记录条目卡片
 */
@Composable
private fun RecordEntryCard(
    entry: RecordInfo,
    attributes: List<RoomAttributeInfo>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium)
        ) {
            // 显示前几个属性值
            /*attributes.take(3).forEach { attr ->
                val value = entry.getValue(attr.id) ?: ""
                if (value.isNotBlank()) {
                    RecordAttributeRow(
                        attributeName = attr.name,
                        value = formatAttributeValue(attr, value),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }*/

            // 如果有更多属性，显示提示
            if (attributes.size > 3) {
                Text(
                    text = "还有 ${attributes.size - 3} 个属性...",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

/**
 * 记录属性行
 */
@Composable
private fun RecordAttributeRow(
    attributeName: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(vertical = 4.dp)
    ) {
        Text(
            text = attributeName,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

/**
 * 格式化属性值显示
 */
private fun formatAttributeValue(attribute: RoomAttributeInfo, value: String): String {
    return when (attribute) {
        is com.elife.room.entity.SwitchRoomAttributeInfo -> {
            if (value.toBooleanStrictOrNull() == true) "是" else "否"
        }
        is com.elife.room.entity.RatingRoomAttributeInfo -> {
            val rating = value.toIntOrNull() ?: 0
            "${rating} / ${attribute.maxValue} 星"
        }
        is com.elife.room.entity.MultiSelectRoomAttributeInfo -> {
            try {
                val list = kotlinx.serialization.json.Json.decodeFromString<List<String>>(value)
                list.joinToString(", ")
            } catch (e: Exception) {
                value
            }
        }
        else -> value
    }
}
