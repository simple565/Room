package com.elife.room.feature.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import com.elife.room.core.ext.defaultId
import com.elife.room.core.ui.composable.RoomTopAppBar
import com.elife.room.core.ui.theme.AppTheme
import com.elife.room.core.ui.theme.iconSize
import com.elife.room.core.ui.theme.spacing
import com.elife.room.core.ui.tooling.UiModePreview
import com.elife.room.entity.SearchPluginInfo
import com.elife.room.shared.Res
import com.elife.room.shared.delete
import com.elife.room.shared.edit
import com.elife.room.shared.search_plugin_empty
import com.elife.room.shared.search_plugin_load_preset
import com.elife.room.shared.search_plugin_preset_loading
import com.elife.room.shared.settings_item_search_plugin_manager
import com.elife.room.shared.verify
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


@Serializable
object SearchPlugManagerRoute : NavKey

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SearchPluginManagerPage(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchPluginManagerViewModel = koinViewModel()
) {
    val dataLoadState by viewModel.pluginList.collectAsStateWithLifecycle()
    val showAddPluginDialogState = remember { mutableStateOf<SearchPluginInfo?>(null) }
    val showVerifyPluginDialogState = remember { mutableStateOf<SearchPluginInfo?>(null) }

    Scaffold(
        modifier = modifier,
        topBar = {
            RoomTopAppBar(
                title = stringResource(Res.string.settings_item_search_plugin_manager),
                onBackClick = onBackClick,
                actions = {
                    IconButton(
                        onClick = { showAddPluginDialogState.value = SearchPluginInfo(Long.defaultId, name = "", url = "") },
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
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            if (dataLoadState.dataList.isEmpty() && dataLoadState.isLoading.not()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(Res.string.search_plugin_empty),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    ElevatedButton(
                        onClick = { viewModel.loadPresetPlugins() },
                        modifier = Modifier.padding(top = MaterialTheme.spacing.small),
                        content = {
                            Text(
                                text = dataLoadState.msg.ifBlank { stringResource(Res.string.search_plugin_load_preset) },
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        horizontal = MaterialTheme.spacing.medium,
                        vertical = MaterialTheme.spacing.small
                    ),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
                ) {
                    items(
                        items = dataLoadState.dataList,
                        key = { listOf(it.id, it.url).hashCode() }) {
                        SearchPluginCard(
                            pluginInfo = it,
                            modifier = Modifier.clip(MaterialTheme.shapes.medium),
                            onActionClick = { res ->
                                when(res) {
                                    Res.string.edit -> { showAddPluginDialogState.value = it  }
                                    Res.string.verify -> { showVerifyPluginDialogState.value = it  }
                                    Res.string.delete -> { viewModel.deletePlugin(it.id) }
                                    else -> {}
                                }
                            }

                        )
                    }
                }

            }
            if (dataLoadState.isLoading) {
                Column(
                    modifier = Modifier.align(alignment = Alignment.Center),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Text(
                        text = dataLoadState.msg.ifBlank { stringResource(Res.string.search_plugin_preset_loading) },
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

            }
        }
    }

    showAddPluginDialogState.value?.also {
        AddEditPluginDialog(
            plugin = it,
            onDismiss = { showAddPluginDialogState.value = null },
            onSave = { viewModel.savePlugin(it) }
        )
    }
    showVerifyPluginDialogState.value?.also {
        SearchByPluginDialog(
            onDismiss = { showVerifyPluginDialogState.value = null },
            pluginList = listOf(it)
        )
    }
}

@Composable
private fun SearchPluginCard(
    pluginInfo: SearchPluginInfo,
    onActionClick: (StringResource) -> Unit,
    modifier: Modifier = Modifier
) {
    val actionPopState = remember { mutableStateOf(false) }

    Row(
        modifier = modifier.then(Modifier.fillMaxWidth())
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = MaterialTheme.shapes.medium
            )
            .padding(MaterialTheme.spacing.medium),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BadgedBox(
                    badge = {
                        Badge(containerColor = if (pluginInfo.enable) Color.Green else Color.Red)
                    },
                    content = {
                        Text(
                            text = pluginInfo.name,
                            modifier = Modifier.padding(end = MaterialTheme.spacing.extraSmall),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                )
            }

            Text(
                text = pluginInfo.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Box {
            IconButton(
                onClick = { actionPopState.value = true },
                content = {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = null
                    )
                }
            )
            PlugMoreActionPop(
                expanded = actionPopState.value,
                onDismissRequest = { actionPopState.value = false },
                onActionClick = {
                    actionPopState.value = false
                    onActionClick(it)
                },
                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small)
            )
        }
    }
}

@Composable
private fun PlugMoreActionPop(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onActionClick: (StringResource) -> Unit,
    modifier: Modifier = Modifier
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
        modifier = modifier
    ) {
        PluginMoreActionMenu(text = Res.string.verify, onClick = onActionClick)
        PluginMoreActionMenu(text = Res.string.delete, onClick = onActionClick)
    }
}

@Composable
private fun PluginMoreActionMenu(text: StringResource, onClick: (StringResource) -> Unit) {
    DropdownMenuItem(
        text = {
            Text(text = stringResource(text))
        },
        onClick = { onClick(text) }
    )
}

@UiModePreview
@Composable
private fun SearchPluginCardPreview() {
    AppTheme {
        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                .padding(MaterialTheme.spacing.large),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
        ) {
            SearchPluginCard(
                pluginInfo = SearchPluginInfo(
                    name = "测试",
                    description = "测试搜索插件",
                    url = ""
                ),
                onActionClick = {}
            )
            SearchPluginCard(
                pluginInfo = SearchPluginInfo(
                    name = "测试",
                    description = "测试搜索插件",
                    url = "",
                    enable = false
                ),
                onActionClick = {}
            )
        }
    }
}
