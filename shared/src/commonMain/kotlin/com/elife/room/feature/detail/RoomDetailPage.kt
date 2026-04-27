package com.elife.room.feature.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import com.elife.room.core.ui.composable.RoomTopAppBar
import com.elife.room.core.ui.theme.spacing
import com.elife.room.entity.RecordInfo
import com.elife.room.shared.Res
import com.elife.room.shared.record_add
import com.elife.room.shared.record_click_to_add
import com.elife.room.shared.record_empty
import com.elife.room.shared.record_list
import com.elife.room.shared.record_statistics
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource
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
    val recordList by viewModel.recordList.collectAsStateWithLifecycle()
    val selectedTab by viewModel.selectedTab.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            RoomTopAppBar(
                title = room?.name ?: "",
                actions = {

                },
                onBackClick = onBackClick
            )
        },
        floatingActionButton = {
            if (selectedTab == RoomDetailTab.RECORDS) {
                SmallFloatingActionButton(
                    onClick = {},
                    shape = CircleShape,
                    content = {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = stringResource(Res.string.record_add)
                        )
                    }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize()
        ) {
            PrimaryTabRow(
                selectedTabIndex = selectedTab.ordinal,
                modifier = Modifier.fillMaxWidth(),
                divider = {}
            ) {
                Tab(
                    selected = selectedTab == RoomDetailTab.RECORDS,
                    onClick = { viewModel.selectTab(RoomDetailTab.RECORDS) },
                    text = { Text(text = stringResource(Res.string.record_list)) },
                )
                Tab(
                    selected = selectedTab == RoomDetailTab.STATS,
                    onClick = { viewModel.selectTab(RoomDetailTab.STATS) },
                    text = { Text(text = stringResource(Res.string.record_statistics)) },
                )
            }

            when (selectedTab) {
                RoomDetailTab.RECORDS -> {
                    RecordListContent(
                        recordList = recordList,
                        onRecordClick = { },
                        modifier = Modifier.fillMaxSize()
                    )
                }

                RoomDetailTab.STATS -> {

                }
            }
        }
    }
}

/**
 * 记录列表内容
 */
@Composable
private fun RecordListContent(
    recordList: List<RecordInfo>,
    onRecordClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    if (recordList.isEmpty()) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(Res.string.record_empty),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = stringResource(Res.string.record_click_to_add),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = MaterialTheme.spacing.small)
            )
        }
    } else {
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(MaterialTheme.spacing.medium),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
        ) {
            items(items = recordList, key = { it.recordId }) {
                RecordInfoCard(
                    record = it,
                    onClick = { onRecordClick(it.recordId) }
                )
            }
        }
    }
}

/**
 * 记录条目卡片
 */
@Composable
private fun RecordInfoCard(
    record: RecordInfo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(MaterialTheme.spacing.medium)) {
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
