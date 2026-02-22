package com.elife.room.feature.settings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.elife.room.core.ui.tooling.DevicePreview

/**
 * @author lianml
 * @date 2025/11/4
 */
@DevicePreview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPage(
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.then(Modifier.fillMaxSize()),
        topBar = {}
    ) { paddingValues ->
    }
}