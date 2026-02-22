package com.elife.room.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation3.runtime.NavKey
import com.elife.room.core.ui.composable.CustomBottomAppBar
import com.elife.room.core.ui.composable.NavigationItem
import com.elife.room.feature.home.HomePage
import com.elife.room.feature.settings.SettingsPage
import com.elife.room.shared.Res
import com.elife.room.shared.nav_home
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
    onDetailClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo()
) {
    var selectedItem by rememberSaveable { mutableStateOf(0) }
    val navigationItemList = listOf(
        NavigationItem(label = stringResource(Res.string.nav_home), icon = Icons.Default.Home),
        NavigationItem(label = stringResource(Res.string.nav_settings), icon = Icons.Default.Settings)
    )
    when (NavigationSuiteScaffoldDefaults.navigationSuiteType(windowAdaptiveInfo)) {
        NavigationSuiteType.ShortNavigationBarCompact, NavigationSuiteType.ShortNavigationBarMedium, NavigationSuiteType.NavigationBar -> {
            // 普通手机屏幕：使用自定义底部导航栏
            val scrollBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior()
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                bottomBar = {
                    CustomBottomAppBar(
                        items = navigationItemList,
                        selectedIndex = selectedItem,
                        onItemSelected = { selectedItem = it },
                        scrollBehavior = scrollBehavior
                    )
                },
                content = { _ ->
                    Box(modifier = Modifier.fillMaxSize()) {
                        when (selectedItem) {
                            0 -> HomePage(
                                onDetailClick = onDetailClick,
                                modifier = Modifier.fillMaxSize()
                            )

                            1 -> SettingsPage()
                        }
                    }
                }
            )
        }

        else -> {
            // 大屏幕：使用NavigationSuiteScaffold
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
                modifier = modifier
            ) {
                when (selectedItem) {
                    0 -> HomePage(
                        onDetailClick = onDetailClick,
                        modifier = Modifier.fillMaxSize()
                    )

                    1 -> SettingsPage()
                }
            }
        }
    }
}