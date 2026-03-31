package com.elife.room.feature.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.elife.room.core.ext.isCompact
import com.elife.room.core.ui.composable.AdaptiveDialog
import com.elife.room.core.ui.theme.spacing
import com.elife.room.entity.RoomTemplate
import com.elife.room.shared.Res
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi


/**
 * 添加房间弹窗
 */
@OptIn(ExperimentalResourceApi::class)
@Composable
fun AddRoomDialog(
    onDismissRequest: () -> Unit,
    onTemplateSelected: (RoomTemplate?) -> Unit,
    modifier: Modifier = Modifier,
    windowInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo()
) {
    var templates by remember { mutableStateOf<List<RoomTemplate>>(emptyList()) }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            templates = Json.decodeFromString<List<RoomTemplate>>(
                Res.readBytes("files/template_room/catalog.json").decodeToString()
            )
        }
    }

    AdaptiveDialog(
        onDismissRequest = onDismissRequest,
        content = {
            Column(modifier = Modifier.fillMaxWidth()) {
                LazyColumn(
                    modifier = modifier.then(
                        Modifier.fillMaxWidth(if (windowInfo.isCompact()) 1F else 0.7F)
                            .fillMaxHeight(if (windowInfo.isCompact()) 0.75F else 0.55F)
                    ),
                    contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.medium, vertical = MaterialTheme.spacing.large),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(templates, key = { it.name }) { template ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = MaterialTheme.spacing.extraSmall)
                                .clickable { onTemplateSelected(template) }
                        ) {
                            Column(
                                modifier = Modifier.padding(MaterialTheme.spacing.large)
                            ) {
                                Text(
                                    text = template.name,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = template.description,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}
