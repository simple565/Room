package com.elife.room.feature.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.elife.room.core.ext.isCompact
import com.elife.room.core.ui.composable.AdaptiveDialog
import com.elife.room.core.ui.composable.CommonDialogTitle
import com.elife.room.core.ui.theme.spacing
import com.elife.room.entity.SearchPluginInfo
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SearchByPluginDialog(
    onDismiss: () -> Unit,
    pluginList: List<SearchPluginInfo>,
    modifier: Modifier = Modifier,
    onResultClick: ((Map<String, String>) -> Unit)? = null,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo()
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedPlugin by remember { mutableStateOf<SearchPluginInfo?>(null) }
    var searchResults by remember { mutableStateOf(emptyList<Map<String, String>>()) }
    var isSearching by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val searchService = remember { SearchService() }

    LaunchedEffect(pluginList) {
        if (pluginList.isNotEmpty() && selectedPlugin == null) {
            selectedPlugin = pluginList.first()
        }
    }

    AdaptiveDialog(
        onDismissRequest = onDismiss,
        content = {
            Column(
                modifier = modifier.then(
                    Modifier
                        .fillMaxWidth(if (windowAdaptiveInfo.isCompact()) 1F else 0.7F)
                        .fillMaxHeight(if (windowAdaptiveInfo.isCompact()) 0.9F else 0.7F)
                )
            ) {
                CommonDialogTitle(
                    title = "按插件搜索",
                    onCancelClick = onDismiss
                )

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("搜索关键词") },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                val plugin = selectedPlugin
                                if (plugin != null && searchQuery.isNotBlank()) {
                                    coroutineScope.launch {
                                        isSearching = true
                                        searchResults = searchService.search(plugin, searchQuery)
                                        isSearching = false
                                    }
                                }
                            },
                            content = {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = "搜索"
                                )
                            }
                        )
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            val plugin = selectedPlugin
                            if (plugin != null && searchQuery.isNotBlank()) {
                                coroutineScope.launch {
                                    isSearching = true
                                    searchResults = searchService.search(plugin, searchQuery)
                                    isSearching = false
                                }
                            }
                        }
                    ),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.spacing.medium)
                        .padding(top = MaterialTheme.spacing.small)
                )

                Text(
                    text = "选择插件",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(
                        start = MaterialTheme.spacing.medium,
                        top = MaterialTheme.spacing.medium,
                        end = MaterialTheme.spacing.medium
                    )
                )

                if (pluginList.isEmpty()) {
                    Text(
                        text = "暂无可用插件，请先在设置中添加",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(MaterialTheme.spacing.medium)
                    )
                } else {
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MaterialTheme.spacing.medium),
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall),
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
                    ) {
                        pluginList.forEach { plugin ->
                            FilterChip(
                                selected = selectedPlugin?.id == plugin.id,
                                onClick = { selectedPlugin = plugin },
                                label = {
                                    Text(
                                        text = plugin.name,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            )
                        }
                    }
                }

                Text(
                    text = "搜索结果",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(
                        start = MaterialTheme.spacing.medium,
                        top = MaterialTheme.spacing.medium,
                        end = MaterialTheme.spacing.medium,
                        bottom = MaterialTheme.spacing.small
                    )
                )

                if (isSearching) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1F),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(32.dp)
                        )
                        Text(
                            text = "搜索中...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = MaterialTheme.spacing.small)
                        )
                    }
                } else if (searchResults.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1F),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = if (searchQuery.isBlank()) "输入关键词开始搜索" else "未找到相关结果",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth().weight(1F),
                        contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.medium),
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
                    ) {
                        items(items = searchResults, key = { it.hashCode() }) {
                            SearchResultItem(
                                result = it,
                                onClick = { onResultClick?.invoke(it) }
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
private fun SearchResultItem(
    result: Map<String, String>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(MaterialTheme.spacing.small),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
    ) {
        result.entries.forEach { (key, value) ->
            if (value.isNotBlank()) {
                Text(
                    text = value,
                    style = if (key == result.keys.first()) {
                        MaterialTheme.typography.bodyLarge
                    } else {
                        MaterialTheme.typography.bodySmall
                    },
                    color = if (key == result.keys.first()) {
                        MaterialTheme.colorScheme.onSurface
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    },
                    maxLines = 2
                )
            }
        }
    }
}
