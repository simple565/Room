package com.elife.room.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.elife.room.core.ui.theme.AppTheme
import com.elife.room.feature.detail.RoomDetailPage
import com.elife.room.feature.detail.RoomDetailRoute
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

private val config = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(IndexRoute::class, IndexRoute.serializer())
            subclass(RoomDetailRoute::class, RoomDetailRoute.serializer())
        }
    }
}

@Composable
fun App() {
    AppTheme {
        val topLevelBackStack = rememberNavBackStack(config,IndexRoute)

        NavDisplay(
            backStack = topLevelBackStack,
            onBack = { topLevelBackStack.removeLastOrNull() },
            entryProvider = entryProvider {
                entry<IndexRoute> {
                    IndexPage(
                        onDetailClick = { topLevelBackStack.add(RoomDetailRoute(it))},
                        modifier = Modifier.fillMaxSize()
                    )
                }
                entry<RoomDetailRoute> {
                    RoomDetailPage(
                        id = it.id,
                        onBackClick = { topLevelBackStack.removeLastOrNull()},
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        )
    }
}