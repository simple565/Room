package com.elife.room.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.elife.room.core.ui.tooling.WindowSizePreview
import com.elife.room.entity.RoomBriefInfo
import com.elife.room.ui.theme.AppTheme
import com.kmp.template.ui.composable.AdaptiveDialog
import org.jetbrains.compose.resources.stringResource
import room.composeapp.generated.resources.Res
import room.composeapp.generated.resources.app_name
import room.composeapp.generated.resources.room_last_update_format

/**
 * @author Lian
 * @date 2025/11/4
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    onDetailClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel { HomeViewModel() }
) {
    val showAddRoomDialog = remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.then(Modifier.fillMaxSize()),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(Res.string.app_name),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontStyle = FontStyle.Italic
                        )
                    )
                },
                actions = {
                    IconButton(
                        onClick = { showAddRoomDialog.value = true },
                        content = {
                            Icon(
                                imageVector = Icons.Rounded.Add,
                                contentDescription = null
                            )
                        }
                    )
                }
            )
        }
    ) { paddingValues ->
        val roomList by viewModel.briefListFlow.collectAsStateWithLifecycle()

        RoomBriefInfoGrid(
            shelfBriefList = roomList,
            modifier = Modifier.padding(paddingValues = paddingValues).fillMaxSize(),
            onClick = { onDetailClick(it.id) }
        )

        if (showAddRoomDialog.value) {
            AdaptiveDialog(
                onDismissRequest = { showAddRoomDialog.value = false },
                usePlatformDefaultWidth = false,
                content = {
                    Column(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight(0.95F),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp)
                        )
                        Text(
                            text = "Add Room",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            )
        }
    }
}

@Composable
private fun RoomBriefInfoGrid(
    shelfBriefList: List<RoomBriefInfo>,
    onClick: (RoomBriefInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    val cardModifier = Modifier.aspectRatio(3.div(2F))
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 120.dp),
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items = shelfBriefList, key = { it.id }) {
            RoomBriefInfoCard(
                briefInfo = it, modifier = cardModifier.combinedClickable(
                    indication = null,
                    interactionSource = null,
                    onClick = { onClick(it) },
                    onLongClick = {}
                )
            )
        }
    }
}

@Composable
private fun RoomBriefInfoCard(briefInfo: RoomBriefInfo, modifier: Modifier = Modifier) {
    ElevatedCard(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxSize().padding(8.dp),
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
                    modifier = Modifier.size(24.dp),
                    shape = RoundedCornerShape(50),
                    content = {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    shape = RoundedCornerShape(50)
                                )
                        )
                    }
                )
            }
        }
    }
}

@WindowSizePreview
@Composable
fun RoomGridPreview() {
    val roomBriefInfo = RoomBriefInfo(id = 1, name = "阅读")
    val list = listOf(roomBriefInfo, roomBriefInfo.copy(id = 2), roomBriefInfo.copy(id = 3))
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        AppTheme(darkTheme = false) {
            RoomBriefInfoGrid(modifier = Modifier, shelfBriefList = list, onClick = {})
        }
        AppTheme(darkTheme = true) {
            RoomBriefInfoGrid(modifier = Modifier, shelfBriefList = list, onClick = {})
        }
    }
}