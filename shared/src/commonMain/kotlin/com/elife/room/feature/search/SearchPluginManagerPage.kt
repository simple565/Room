package com.elife.room.feature.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel


@Serializable
object SearchPlugManagerRoute : NavKey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchPluginManagerPage(
    viewModel: SearchPluginManagerViewModel = koinViewModel()
) {
    val pluginList by viewModel.pluginList.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("搜索插件管理") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {}
            ) {
                Icon(Icons.Default.Add, contentDescription = "添加插件")
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            if (pluginList.isEmpty()) {
                Text(
                    text = "还没有添加搜索插件",
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                LazyColumn {
                    items(items = pluginList, key = { it.id }) {

                    }
                }
            }
        }
    }
}
