package com.elife.room.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.elife.room.feature.detail.RoomDetailPage
import com.elife.room.ui.theme.AppTheme

@Composable
fun App() {
    AppTheme {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = IndexPage) {
            composable<IndexPage> {
                IndexPage(
                    navController = navController,
                    modifier = Modifier.fillMaxSize()
                )
            }
            composable<RoomDetailPage> {
                RoomDetailPage(
                    id = it.toRoute<RoomDetailPage>().id,
                    onBackClick = navController::popBackStack,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}