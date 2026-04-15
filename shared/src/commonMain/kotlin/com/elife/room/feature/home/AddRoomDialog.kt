package com.elife.room.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.elife.room.core.ext.isCompact
import com.elife.room.core.ui.composable.AdaptiveDialog
import com.elife.room.core.ui.composable.CommonDialogTitle
import com.elife.room.core.ui.theme.spacing
import com.elife.room.entity.RoomTemplate
import com.elife.room.shared.Res
import com.elife.room.shared.room_config_add
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource


/**
 * 添加房间弹窗
 */
@OptIn(ExperimentalResourceApi::class)
@Composable
fun AddRoomDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    windowInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo()
) {
    val templates = remember { mutableStateOf<List<RoomTemplate>>(emptyList()) }
    val showEditRoomDialogState = remember { mutableStateOf<String?>(null)}

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            templates.value = Json.decodeFromString<List<RoomTemplate>>(
                Res.readBytes("files/template_room/catalog.json").decodeToString()
            )
        }
    }

    AdaptiveDialog(
        onDismissRequest = onDismissRequest,
        content = {
            Column(
                modifier = modifier.then(
                    Modifier.fillMaxWidth(if (windowInfo.isCompact()) 1F else 0.7F)
                        .fillMaxHeight(if (windowInfo.isCompact()) 0.75F else 0.55F)
                )
            ) {
                CommonDialogTitle(
                    title = stringResource(Res.string.room_config_add),
                    onCancelClick = onDismissRequest
                )
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(MaterialTheme.spacing.medium),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(items = templates.value, key = { it.name }) { template ->
                        Column(
                            modifier = Modifier.fillMaxWidth()
                                .background(color = MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.medium)
                                .clip(shape = MaterialTheme.shapes.medium)
                                .clickable { showEditRoomDialogState.value = template.file }
                                .padding(MaterialTheme.spacing.large)
                        ) {
                            Text(
                                text = template.name,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = template.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                    }
                }
            }
        }
    )

    if (showEditRoomDialogState.value != null) {
        EditRoomConfigDialog(
            templateFileName = showEditRoomDialogState.value!!,
            onDismissRequest = {
                showEditRoomDialogState.value = null
                if (it != null) {
                    onDismissRequest()
                }
            },
            windowInfo = windowInfo
        )
    }
}
