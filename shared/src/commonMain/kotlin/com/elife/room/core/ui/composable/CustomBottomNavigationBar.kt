package com.elife.room.core.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarScrollBehavior
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.elife.room.core.ui.theme.AppTheme
import com.elife.room.core.ui.tooling.UiModePreview

/**
 * 自定义底部导航栏项数据类
 */
data class NavigationItem(
    val label: String,
    val icon: ImageVector
)

/**
 * 自定义底部导航栏组件
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBottomAppBar(
    items: List<NavigationItem>,
    selectedIndex: Int,
    onItemSelected: (index: Int) -> Unit,
    modifier: Modifier = Modifier,
    showLabels: Boolean = true,
    scrollBehavior: BottomAppBarScrollBehavior? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    selectedColor: Color = MaterialTheme.colorScheme.surfaceContainerHighest,
    unselectedColor: Color = MaterialTheme.colorScheme.surface
) {
    BottomAppBar(
        modifier = Modifier,
        containerColor = Color.Transparent,
        scrollBehavior = scrollBehavior
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = modifier
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.1F),
                        shape = MaterialTheme.shapes.extraLarge
                    )
                    .background(backgroundColor, shape = MaterialTheme.shapes.extraLarge)
                    .padding(4.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEachIndexed { index, item ->
                    CustomBottomAppBarItem(
                        item = item,
                        modifier = Modifier
                            .widthIn(min = 80.dp)
                            .height(50.dp)
                            .background(
                                color = if (index == selectedIndex) selectedColor else unselectedColor,
                                shape = MaterialTheme.shapes.extraLarge
                            ).clickable(
                                indication = null,
                                interactionSource = null,
                                onClick = {
                                    onItemSelected(index)
                                }
                            ).padding(horizontal = 16.dp),
                        showLabel = showLabels,
                        contentColor = if (index == selectedIndex) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface

                    )
                }
            }
        }
    }
}

/**
 * 自定义底部导航项
 */
@Composable
private fun CustomBottomAppBarItem(
    item: NavigationItem,
    modifier: Modifier = Modifier,
    showLabel: Boolean = true,
    contentColor: Color = MaterialTheme.colorScheme.onSurface
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.label,
            tint = contentColor,
            modifier = Modifier.size(24.dp)
        )

        if (showLabel) {
            Text(
                text = item.label,
                color = contentColor,
                overflow = TextOverflow.MiddleEllipsis,
                maxLines = 1,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@UiModePreview
@Composable
private fun CustomBottomAppBarPreview() {
    AppTheme {
        Column(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
            CustomBottomAppBar(
                items = listOf(
                    NavigationItem(label = "Home", icon = Icons.Rounded.Home),
                    NavigationItem(label = "Settings", icon = Icons.Rounded.Settings)
                ),
                selectedIndex = 0,
                onItemSelected = { },
                modifier = Modifier
            )
        }
    }
}