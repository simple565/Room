package com.elife.room.feature.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.elife.room.database.table.SearchPlugin
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchPage(
    pluginIds: List<Long>,
    modifier: Modifier = Modifier,
    viewModel: PluginSearchViewModel = koinViewModel(),
) {
    var pluginList by remember { mutableStateOf(emptyList<SearchPlugin>())}
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    var searchQuery by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(pluginList) {
        viewModel.getPluginListFlow(pluginIds).collect {
            pluginList = it
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("搜索") }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("搜索关键词") },
                trailingIcon = {
                    IconButton(
                        onClick = {},
                        content = {
                            Icon(Icons.Default.Search, contentDescription = "搜索")
                        }
                    )
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {

                    }
                ),
                modifier = Modifier.fillMaxWidth()
            )
            TODO("显示支持的插件TAB")

            if (pluginList.isEmpty()) {
                Text(
                    text = "请先在设置中添加搜索插件",
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            } else if (isLoading) {
                Text(
                    text = "搜索中...",
                    modifier = Modifier.padding(top = 8.dp)
                )
            } else {
                TODO("显示搜索结果")
            }
        }
    }
}
