package com.elife.room.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.elife.room.core.ui.composable.RoomTopAppBar
import com.elife.room.core.ui.theme.AppTheme
import com.elife.room.core.ui.theme.iconSize
import com.elife.room.core.ui.theme.spacing
import com.elife.room.core.ui.tooling.UiModePreview
import com.elife.room.database.table.RoomGroup
import com.elife.room.entity.RoomBriefInfo
import com.elife.room.shared.Res
import com.elife.room.shared.app_name
import com.elife.room.shared.room_last_update_format
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

/**
 * @author Lian
 * @date 2025/11/4
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HomePage(
    onDetailClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    windowInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
    viewModel: HomeViewModel = koinViewModel()
) {
    val roomGroupTabState by viewModel.groupTabsState.collectAsStateWithLifecycle()
    val roomList by viewModel.briefListFlow.collectAsStateWithLifecycle()
    val showAddRoomDialog = remember { mutableStateOf(false) }
    val showRoomMoreActionDialog = remember { mutableStateOf<RoomBriefInfo?> (null) }

    Scaffold(
        modifier = modifier.then(Modifier.fillMaxSize().systemBarsPadding()),
        topBar = {
            RoomTopAppBar(
                title = stringResource(Res.string.app_name),
                actions = {
                    IconButton(
                        onClick = { showAddRoomDialog.value = true },
                        content = {
                            Icon(
                                imageVector = Icons.Rounded.Add,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.size(MaterialTheme.iconSize.medium)
                            )
                        }
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
        ) {
            RoomGroupTabRow(
                selectedIndex = roomGroupTabState.selectedIndex,
                groupList = roomGroupTabState.groupList,
                onTabClick = viewModel::selectGroupTab,
                modifier = Modifier.fillMaxWidth()
            )
            RoomBriefInfoGrid(
                shelfBriefList = roomList.roomList,
                modifier = Modifier.fillMaxSize(),
                onItemClick = { onDetailClick(it.id) },
                onItemLongClick = { showRoomMoreActionDialog.value = it }
            )
        }
    }

    if (showAddRoomDialog.value) {
        AddRoomDialog(
            onDismissRequest = { showAddRoomDialog.value = false },
            windowInfo = windowInfo
        )
    }

    showRoomMoreActionDialog.value?.also {
        EditRoomConfigDialog(
            roomId = it.id,
            onDismissRequest = { showRoomMoreActionDialog.value = null },
            windowInfo = windowInfo
        )
    }
}

@Composable
private fun RoomGroupTabRow(
    selectedIndex: Int,
    groupList: List<RoomGroup>,
    onTabClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        PrimaryScrollableTabRow(
            selectedTabIndex = selectedIndex,
            modifier = Modifier.weight(1F),
            edgePadding = 6.dp,
            indicator = {},
            divider = {},
            minTabWidth = 70.dp,
            tabs = {
                groupList.forEachIndexed { index, group ->
                    Tab(
                        selected = index == selectedIndex,
                        onClick = { onTabClick(index) },
                        modifier = Modifier
                            .padding(horizontal = MaterialTheme.spacing.extraSmall)
                            .height(28.dp)
                            .clip(shape = MaterialTheme.shapes.medium)
                            .background(
                                color = if (index == selectedIndex) {
                                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3F)
                                } else {
                                    MaterialTheme.colorScheme.surfaceContainer
                                }
                            ),
                        text = {
                            Text(
                                text = group.name,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(if (index == selectedIndex) 1F else 0.5F)
                            )
                        }
                    )
                }
            }
        )
    }
}

@Composable
private fun RoomBriefInfoGrid(
    shelfBriefList: List<RoomBriefInfo>,
    onItemClick: (RoomBriefInfo) -> Unit,
    onItemLongClick: (RoomBriefInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    val haptics = LocalHapticFeedback.current

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 120.dp),
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.medium),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
    ) {
        items(items = shelfBriefList, key = { it.id }) {
            RoomBriefInfoCard(
                    briefInfo = it,
                    modifier = Modifier.aspectRatio(3.div(2F)).combinedClickable(
                        indication = null,
                        interactionSource = null,
                        onClick = { onItemClick(it) },
                        onLongClick = {
                            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                            onItemLongClick(it)
                        }
                    )
                )
        }
    }
}

@Composable
private fun RoomBriefInfoCard(briefInfo: RoomBriefInfo, modifier: Modifier = Modifier) {
    ElevatedCard(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxSize().padding(MaterialTheme.spacing.medium),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = briefInfo.name, style = MaterialTheme.typography.titleSmall)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(
                        Res.string.room_last_update_format,
                        briefInfo.lastUpdateTime
                    ),
                    style = MaterialTheme.typography.labelSmall
                        .copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                )
                IconButton(
                    onClick = {},
                    modifier = Modifier.size(MaterialTheme.iconSize.medium),
                    content = {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            contentDescription = null,
                            modifier = Modifier.size(MaterialTheme.iconSize.small)
                                .background(
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    shape = CircleShape
                                )
                        )
                    }
                )
            }
        }
    }
}

@UiModePreview
@Composable
private fun RoomGroupTabRowPreview() {
    val groupList = listOf("全部", "学习", "娱乐", "工作").map {
        RoomGroup(0, it)
    }
    AppTheme {
        Surface {
            RoomGroupTabRow(
                selectedIndex = 0,
                groupList = groupList,
                onTabClick = { },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@UiModePreview
@Composable
private fun RoomGridPreview() {
    val roomBriefInfo = RoomBriefInfo(id = 1, name = "阅读")
    val list = listOf(roomBriefInfo, roomBriefInfo.copy(id = 2), roomBriefInfo.copy(id = 3))
    AppTheme {
        Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)) {
            RoomBriefInfoGrid(
                modifier = Modifier,
                shelfBriefList = list,
                onItemClick = {},
                onItemLongClick = {}
            )
        }
    }
}