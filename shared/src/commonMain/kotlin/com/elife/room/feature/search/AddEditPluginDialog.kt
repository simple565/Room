package com.elife.room.feature.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.elife.room.core.ext.isCompact
import com.elife.room.core.ui.composable.AdaptiveDialog
import com.elife.room.core.ui.composable.CommonDialogTitle
import com.elife.room.core.ui.composable.DropdownSelector
import com.elife.room.core.ui.composable.OutlinedTextField
import com.elife.room.core.ui.theme.spacing
import com.elife.room.entity.SearchParameter
import com.elife.room.entity.SearchPluginInfo
import com.elife.room.feature.search.composable.KeyValueEditor
import com.elife.room.feature.search.composable.ResponseMappingEditor
import com.elife.room.shared.Res
import com.elife.room.shared.add
import com.elife.room.shared.edit
import com.elife.room.shared.search_plugin_action_format
import com.elife.room.shared.search_plugin_body
import com.elife.room.shared.search_plugin_description
import com.elife.room.shared.search_plugin_headers
import com.elife.room.shared.search_plugin_item_regex
import com.elife.room.shared.search_plugin_mapping
import com.elife.room.shared.search_plugin_method
import com.elife.room.shared.search_plugin_name
import com.elife.room.shared.search_plugin_params
import com.elife.room.shared.search_plugin_url
import io.ktor.http.HttpMethod
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditPluginDialog(
    onDismiss: () -> Unit,
    onSave: (SearchPluginInfo) -> Unit,
    modifier: Modifier = Modifier,
    plugin: SearchPluginInfo? = null,
    viewModel: SearchPluginManagerViewModel = koinViewModel(),
    windowInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfoV2()
) {
    val pluginState = remember { mutableStateOf(SearchPluginInfo()) }
    LaunchedEffect(plugin) {
        pluginState.value = plugin ?: SearchPluginInfo()
    }

    AdaptiveDialog(
        onDismissRequest = onDismiss,
        content = {
            Column(
                modifier = modifier.then(
                    Modifier.fillMaxWidth(if (windowInfo.isCompact()) 1F else 0.7F)
                        .fillMaxHeight(0.9F)
                )
            ) {
                CommonDialogTitle(
                    title = stringResource(
                        Res.string.search_plugin_action_format,
                        stringResource(if (plugin == null) Res.string.add else Res.string.edit)
                    ),
                    onCancelClick = onDismiss,
                    onConfirmClick = { onSave(pluginState.value) }
                )
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().padding(MaterialTheme.spacing.medium),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
                ) {
                    item {
                        OutlinedTextField(
                            value = pluginState.value.name,
                            onValueChange = {
                                pluginState.value = pluginState.value.copy(name = it)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            label = stringResource(Res.string.search_plugin_name),
                            isRequired = true,
                            singleLine = true
                        )
                    }
                    item {
                        OutlinedTextField(
                            value = pluginState.value.description,
                            onValueChange = {
                                pluginState.value = pluginState.value.copy(description = it)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            label = stringResource(Res.string.search_plugin_description),
                            isRequired = false,
                            singleLine = true
                        )
                    }
                    item {
                        MethodAndUrlInput(
                            method = pluginState.value.method,
                            url = pluginState.value.url,
                            onMethodChange = {
                                pluginState.value = pluginState.value.copy(method = it)
                            },
                            onUrlChange = { pluginState.value = pluginState.value.copy(url = it) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item {
                        Text(
                            text = stringResource(Res.string.search_plugin_headers),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        KeyValueEditor(
                            items = pluginState.value.headers.map { (key, value) ->
                                SearchParameter(
                                    key = key,
                                    value = value
                                )
                            },
                            onValueChange = { newList ->
                                pluginState.value = pluginState.value.copy(
                                    headers = newList.associate { it.key to it.value }
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item {
                        Text(
                            text = stringResource(Res.string.search_plugin_params),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        KeyValueEditor(
                            items = pluginState.value.params,
                            onValueChange = {
                                pluginState.value = pluginState.value.copy(params = it)
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item {
                        Text(
                            text = stringResource(Res.string.search_plugin_body),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        KeyValueEditor(
                            items = pluginState.value.body,
                            onValueChange = {
                                pluginState.value = pluginState.value.copy(body = it)
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item {
                        OutlinedTextField(
                            value = pluginState.value.itemRegex,
                            onValueChange = {
                                pluginState.value = pluginState.value.copy(itemRegex = it)
                            },
                            label = stringResource(Res.string.search_plugin_item_regex),
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                    item {
                        Text(
                            text = stringResource(Res.string.search_plugin_mapping),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        ResponseMappingEditor(
                            items = pluginState.value.mapping,
                            onValueChange = {
                                pluginState.value = pluginState.value.copy(mapping = it)
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    )
}

@Composable
private fun MethodAndUrlInput(
    method: String,
    url: String,
    onMethodChange: (String) -> Unit,
    onUrlChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DropdownSelector(
            selected = method,
            options = listOf(HttpMethod.Get.value, HttpMethod.Post.value),
            onSelected = onMethodChange,
            modifier = Modifier.weight(0.35F),
            label = stringResource(Res.string.search_plugin_method)
        )
        OutlinedTextField(
            value = url,
            onValueChange = onUrlChange,
            modifier = Modifier.weight(0.65F),
            label = stringResource(Res.string.search_plugin_url),
            isRequired = true,
            singleLine = true
        )
    }
}
