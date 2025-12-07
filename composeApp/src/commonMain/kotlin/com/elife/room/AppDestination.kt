package com.elife.room

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.StringResource
import room.composeapp.generated.resources.Res
import room.composeapp.generated.resources.nav_home
import room.composeapp.generated.resources.nav_settings

/**
 * @author lianml
 * @date 2025/11/29
 */
enum class AppDestinations(
    val label: StringResource,
    val icon: ImageVector,
    val contentDescription: StringResource
) {
    HOME(Res.string.nav_home, Icons.Default.Home, Res.string.nav_home),
    SETTINGS(Res.string.nav_settings, Icons.Default.Settings, Res.string.nav_settings),
}