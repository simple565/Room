package com.elife.room.feature.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey

/**
 * @author lianml
 * @date 2025/11/4
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPage(
    modifier: Modifier = Modifier,
    onNavigateToTarget: (NavKey) -> Unit
) {
    Scaffold(
        modifier = modifier.then(Modifier.fillMaxSize()),
        topBar = {
            TopAppBar(
                title = { Text("设置") }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            /*ListItem(
                headlineContent = { Text("管理图书、电影等搜索插件") },
                onClick = onNavigateToSearchPluginManager
            )*/
        }
    }
}