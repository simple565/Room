package com.elife.room.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.NavigateNext
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.navigation3.runtime.NavKey
import com.elife.room.core.ui.composable.RoomTopAppBar
import com.elife.room.core.ui.theme.AppTheme
import com.elife.room.core.ui.theme.spacing
import com.elife.room.core.ui.tooling.UiModePreview
import com.elife.room.feature.search.SearchPlugManagerRoute
import com.elife.room.shared.Res
import com.elife.room.shared.nav_settings
import com.elife.room.shared.settings_item_search_plugin_manager
import com.elife.room.shared.settings_item_search_plugin_manager_description
import org.jetbrains.compose.resources.stringResource

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
            RoomTopAppBar(title = stringResource(Res.string.nav_settings))
        }
    ) {
        val itemModifier = Modifier.fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceContainerLow,
                shape = MaterialTheme.shapes.medium
            )
            .clip(shape = MaterialTheme.shapes.medium)

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(it),
            contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.large),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
        ) {
            item {
                RouteSettingCard(
                    title = stringResource(Res.string.settings_item_search_plugin_manager),
                    description = stringResource(Res.string.settings_item_search_plugin_manager_description),
                    onClick = { onNavigateToTarget(SearchPlugManagerRoute) },
                    modifier = itemModifier
                )
            }
        }
    }
}

@Composable
private fun RouteSettingCard(
    title: String,
    description: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = CardDefaults.shape,
    colors: CardColors = CardDefaults.cardColors(),
    elevation: CardElevation = CardDefaults.cardElevation(),
) {
    Card(
        modifier = modifier.clickable { onClick() },
        shape = shape,
        colors = colors,
        elevation = elevation
    ) {
        Row(
            modifier = Modifier.padding(MaterialTheme.spacing.medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.weight(1F))
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.NavigateNext,
                contentDescription = title
            )
        }
    }
}

@UiModePreview
@Composable
private fun RouteSettingCardPreview() {
    AppTheme {
        LazyColumn {
            item {
                RouteSettingCard(
                    title = stringResource(Res.string.settings_item_search_plugin_manager),
                    description = stringResource(Res.string.settings_item_search_plugin_manager_description),
                    onClick = {},
                    modifier = Modifier.padding(MaterialTheme.spacing.small).fillMaxWidth()
                )
            }
            item {
                RouteSettingCard(
                    title = stringResource(Res.string.settings_item_search_plugin_manager),
                    description = stringResource(Res.string.settings_item_search_plugin_manager_description),
                    onClick = {},
                    modifier = Modifier.padding(MaterialTheme.spacing.small).fillMaxWidth()
                )
            }
        }
    }
}