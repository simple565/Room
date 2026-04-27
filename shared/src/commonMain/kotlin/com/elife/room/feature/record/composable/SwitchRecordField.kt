package com.elife.room.feature.record.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.elife.room.core.ui.theme.AppTheme
import com.elife.room.core.ui.tooling.UiModePreview
import com.elife.room.entity.SwitchRoomAttributeInfo

/**
 * 开关类型记录字段
 * @author Lian
 * @date 2026/4/4
 */
@Composable
fun SwitchRecordField(
    attribute: SwitchRoomAttributeInfo,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val isChecked = value.toBooleanStrictOrNull() ?: false

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
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
        }
        Switch(
            checked = isChecked,
            onCheckedChange = { onValueChange(it.toString()) },
            enabled = enabled
        )
    }
}

@UiModePreview
@Composable
private fun SwitchRecordFieldPreview() {
    AppTheme {
        SwitchRecordField(
            attribute = SwitchRoomAttributeInfo(
                name = "Sample Switch",
                description = "A sample switch field",
                required = false,
                defaultValue = "false",
                sortOrder = 0
            ),
            value = "false",
            onValueChange = {}
        )
    }
}
