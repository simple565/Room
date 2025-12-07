package com.elife.room.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.rememberNavigationSuiteScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.elife.room.AppDestinations
import com.elife.room.feature.detail.RoomDetailPage
import com.elife.room.feature.home.HomePage
import com.elife.room.feature.settings.SettingsPage
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource

/**
 * @author Lian
 * @date 2025/12/6
 */

@Serializable
object IndexPage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndexPage(navController: NavController, modifier: Modifier = Modifier) {
    val customNavSuiteItemColors = NavigationSuiteDefaults.itemColors(
        navigationBarItemColors = NavigationBarItemDefaults.colors(
            indicatorColor = Color.Transparent,
            selectedIconColor = MaterialTheme.colorScheme.onSurface,
            selectedTextColor = MaterialTheme.colorScheme.onSurface,
            unselectedIconColor = MaterialTheme.colorScheme.outlineVariant,
            unselectedTextColor = MaterialTheme.colorScheme.outlineVariant
        ),
    )
    val customNavSuiteState = rememberNavigationSuiteScaffoldState()
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(
                            imageVector = it.icon,
                            contentDescription = stringResource(it.contentDescription)
                        )
                    },
                    label = {
                        Text(stringResource(it.label))
                    },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it },
                    colors = customNavSuiteItemColors
                )
            }
        },
        modifier = modifier,
        state = customNavSuiteState
    ) {
        when (currentDestination) {
            AppDestinations.HOME -> {
                HomePage(
                    onDetailClick = { navController.navigate(RoomDetailPage(it)) },
                    modifier = Modifier.fillMaxSize()
                )
            }

            AppDestinations.SETTINGS -> {
                SettingsPage(modifier = Modifier.fillMaxSize())
            }
        }
    }
}