package com.elife.room.feature.record.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.elife.room.core.ui.theme.AppTheme
import com.elife.room.core.ui.tooling.UiModePreview
import com.elife.room.entity.DurationRoomAttributeInfo
import com.elife.room.entity.constants.DurationUnit

/**
 * 时长类型记录字段
 * @author Lian
 * @date 2026/4/4
 */
@Composable
fun DurationRecordField(
    attribute: DurationRoomAttributeInfo,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    // 解析时长值（存储为秒数）
    val totalSeconds = value.toIntOrNull() ?: 0
    val supportedUnits = attribute.supportedUnits

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = attribute.name,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        if (attribute.description.isNotBlank()) {
            Text(
                text = attribute.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // 根据支持的单位显示输入框
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            supportedUnits.forEach { unitName ->
                val unit = DurationUnit.entries.find { it.name == unitName } ?: DurationUnit.HOUR
                val unitValue = when (unit) {
                    DurationUnit.SECOND -> totalSeconds
                    DurationUnit.MINUTE -> totalSeconds / 60
                    DurationUnit.HOUR -> totalSeconds / 3600
                    DurationUnit.DAY -> totalSeconds / 86400
                    DurationUnit.MONTH -> totalSeconds / 2592000
                    DurationUnit.YEAR -> totalSeconds / 31536000
                }

                OutlinedTextField(
                    value = if (unitValue > 0) unitValue.toString() else "",
                    onValueChange = { newValue ->
                        val numValue = newValue.toIntOrNull() ?: 0
                        val newTotalSeconds = when (unit) {
                            DurationUnit.SECOND -> numValue
                            DurationUnit.MINUTE -> numValue * 60
                            DurationUnit.HOUR -> numValue * 3600
                            DurationUnit.DAY -> numValue * 86400
                            DurationUnit.MONTH -> numValue * 2592000
                            DurationUnit.YEAR -> numValue * 31536000
                        }
                        onValueChange(newTotalSeconds.toString())
                    },
                    label = { Text(unit.displayName) },
                    enabled = enabled,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    singleLine = true,
     keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )
                }
            }
        }
}

@UiModePreview
@Composable
private fun DurationRecordFieldPreview() {
    AppTheme {
        DurationRecordField(
            attribute = DurationRoomAttributeInfo(
                name = "Sample Duration",
                description = "A sample duration field",
                required = false,
                defaultValue = "",
                supportedUnits = listOf("HOUR", "MINUTE", "SECOND"),
                sortOrder = 0
            ),
            value = "3600",
            onValueChange = {}
        )
    }
}