package com.elife.room.feature.home

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.elife.room.core.ui.composable.AdaptiveDialog

/**
 * 列表样式配置对话框
 * @date 2026/2/23
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomListDisplayConfigDialog(
    onDismissRequest: () -> Unit,
    onColumnValueChange: (Int) -> Unit
) {
    AdaptiveDialog(
        onDismissRequest = onDismissRequest,
        usePlatformDefaultWidth = false,
        content = {
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.Center
            ) {
                val columnCount = remember { mutableStateOf(0F) }
                val interactionSource = remember { MutableInteractionSource() }
                Text(
                    text = "列表显示模式",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = when (columnCount.value) {
                            0F -> "自适应"
                            1F -> "列表"
                            else -> "网格"
                        },
                        style = MaterialTheme.typography.bodyMedium
                    )
                    if (columnCount.value > 1) {
                        Text(
                            text = "${columnCount.value.toInt()}列",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Slider(
                    value = columnCount.value,
                    onValueChange = {
                        columnCount.value = it
                        onColumnValueChange(it.toInt())
                    },
                    modifier = Modifier.fillMaxWidth(),
                    valueRange = 0F..10F,
                    steps = 9,
                    interactionSource = interactionSource,
                    thumb = {
                        SliderDefaults.Thumb(
                            interactionSource = interactionSource,
                            thumbSize = DpSize(6.dp, 28.dp)
                        )
                    }
                )
            }
        }
    )
}