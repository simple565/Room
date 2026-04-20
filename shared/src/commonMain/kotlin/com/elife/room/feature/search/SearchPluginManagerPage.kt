package com.elife.room.feature.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import com.elife.room.core.ui.composable.RoomTopAppBar
import com.elife.room.core.ui.theme.iconSize
import com.elife.room.shared.Res
import com.elife.room.shared.search_plugin_empty
import com.elife.room.shared.settings_item_search_plugin_manager
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


@Serializable
object SearchPlugManagerRoute : NavKey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchPluginManagerPage(
    onBackClick: () -> Unit,
    viewModel: SearchPluginManagerViewModel = koinViewModel()
) {
    val pluginList by viewModel.pluginList.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            RoomTopAppBar(
                title = stringResource(Res.string.settings_item_search_plugin_manager),
                onBackClick = onBackClick,
                actions = {
                    IconButton(
                        onClick = { },
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
        if (pluginList.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                Text(
                    text = stringResource(Res.string.search_plugin_empty),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(alignment = Alignment.Center)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(innerPadding)
            ) {
                items(items = pluginList, key = { it.id }) {

                }
            }

        }
    }
}
