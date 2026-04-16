package com.elife.room.core.ui.composable

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elife.room.shared.Res
import com.elife.room.shared.back
import org.jetbrains.compose.resources.stringResource

/**
 * @date 2026/4/16
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
        },
        modifier = modifier,
        navigationIcon = {
            if (null != onBackClick) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = stringResource(Res.string.back)
                    )
                }
            }
        },
        actions = actions,
        expandedHeight = 54.dp
    )
}