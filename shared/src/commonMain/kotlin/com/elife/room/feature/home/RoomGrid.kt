package com.elife.room.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.elife.room.core.ui.theme.AppTheme
import com.elife.room.core.ui.theme.iconSize
import com.elife.room.core.ui.theme.spacing
import com.elife.room.core.ui.tooling.UiModePreview
import com.elife.room.entity.RoomBriefInfo
import com.elife.room.shared.Res
import com.elife.room.shared.delete
import com.elife.room.shared.room_config_edit
import com.elife.room.shared.room_last_update_format
import org.jetbrains.compose.resources.stringResource

/**
 * @author Lian
 * @date 2026/4/21
 */
@Composable
fun RoomBriefInfoGrid(
    shelfBriefList: List<RoomBriefInfo>,
    onItemClick: (RoomBriefInfo) -> Unit,
    onItemEditClick: (RoomBriefInfo) -> Unit,
    onItemDeleteClick: (RoomBriefInfo) -> Unit,
    onRecordAddClick: (RoomBriefInfo) -> Unit,
    modifier: Modifier = Modifier
) {
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
                modifier = Modifier.aspectRatio(3.div(2F)),
                onItemClick = { onItemClick(it) },
                onItemEditClick = { onItemEditClick(it) },
                onItemDeleteClick = { onItemDeleteClick(it) },
                onRecordAddClick = { onRecordAddClick(it) }
            )
        }
    }
}

@Composable
private fun RoomBriefInfoCard(
    briefInfo: RoomBriefInfo,
    onItemClick: () -> Unit,
    onItemEditClick: () -> Unit,
    onItemDeleteClick: () -> Unit,
    onRecordAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val haptics = LocalHapticFeedback.current
    val showMoreActionPop = remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        ElevatedCard(
            modifier = modifier.combinedClickable(
                indication = null,
                interactionSource = null,
                onClick = onItemClick,
                onLongClick = {
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                    showMoreActionPop.value = true
                }
            )
        ) {
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
                        onClick = onRecordAddClick,
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

        if (showMoreActionPop.value) {
            RoomMoreActionMenu(
                expand = showMoreActionPop.value,
                modifier = Modifier.width(180.dp).wrapContentHeight(),
                onEditClick = {
                    onItemEditClick()
                    showMoreActionPop.value = false
                },
                onDeleteClick = {
                    onItemDeleteClick()
                    showMoreActionPop.value = false
                },
                onDismissRequest = {
                    showMoreActionPop.value = false
                }
            )
        }
    }
}

@Composable
private fun RoomMoreActionMenu(
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    expand: Boolean = false
) {
    DropdownMenu(
        expanded = expand,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        offset = DpOffset(0.dp, MaterialTheme.spacing.small),
        shape = MaterialTheme.shapes.small,
        shadowElevation = 12.dp
    ) {
        Column {
            DropdownMenuItem(
                text = {
                    Text(
                        text = stringResource(Res.string.room_config_edit),
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                onClick = onEditClick,
                modifier = Modifier.wrapContentHeight(),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = null,
                        modifier = Modifier.size(MaterialTheme.iconSize.small)
                    )
                }
            )

            DropdownMenuItem(
                text = {
                    Text(
                        text = stringResource(Res.string.delete),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                },
                onClick = onDeleteClick,
                modifier = Modifier.wrapContentHeight(),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = null,
                        modifier = Modifier.size(MaterialTheme.iconSize.small),
                        tint = MaterialTheme.colorScheme.error
                    )
                }
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
                onItemEditClick = {},
                onItemDeleteClick = {},
                onRecordAddClick = {}
            )
        }
    }
}