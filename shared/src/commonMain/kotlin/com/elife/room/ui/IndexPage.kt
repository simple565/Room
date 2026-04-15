package com.elife.room.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import com.elife.room.core.ui.composable.NavigationItem
import com.elife.room.feature.home.HomePage
import com.elife.room.feature.settings.SettingsPage
import com.elife.room.shared.Res
import com.elife.room.shared.nav_home
import com.elife.room.shared.nav_log
import com.elife.room.shared.nav_settings
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource

/**
 * @author Lian
 * @date 2025/12/6
 */
@Serializable
object IndexRoute : NavKey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndexPage(
    onDetailClick: (Long) -> Unit = {},
    modifier: Modifier = Modifier,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo()
) {
    var selectedItem by rememberSaveable { mutableStateOf(0) }
    val navigationItemList = listOf(
        NavigationItem(label = stringResource(Res.string.nav_home), icon = Icons.Rounded.Home),
        NavigationItem(label = stringResource(Res.string.nav_log), icon = Icons.AutoMirrored.Filled.ListAlt),
        NavigationItem(label = stringResource(Res.string.nav_settings), icon = Icons.Rounded.Settings)
    )
    NavigationSuiteScaffold(
        navigationSuiteItems = {
            navigationItemList.forEachIndexed { index, item ->
                item(
                    selected = selectedItem == index,
                    onClick = { selectedItem = index },
                    icon = {
                        Icon(imageVector = item.icon, contentDescription = item.label)
                    },
                    label = { Text(item.label) }
                )
            }
        },
        modifier = modifier,
        layoutType = NavigationSuiteScaffoldDefaults.navigationSuiteType(windowAdaptiveInfo)
    ) {
        when (selectedItem) {
            0 -> HomePage(
                onDetailClick = onDetailClick,
                modifier = Modifier.fillMaxSize()
            )

            1 -> SettingsPage(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}