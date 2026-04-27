package com.elife.room.feature.record.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.elife.room.core.ui.theme.AppTheme
import com.elife.room.core.ui.tooling.UiModePreview
import com.elife.room.entity.NumberRoomAttributeInfo

/**
 * 数字类型记录字段
 * @author Lian
 * @date 2026/4/4
 */
@Composable
fun NumberRecordField(
    attribute: NumberRoomAttributeInfo,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    error: String? = null
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = { newValue ->
                    // 只允许输入数字和小数点
                    val filtered = if (attribute.allowDecimal) {
                        newValue.filter { it.isDigit() || it == '.' }
                    } else {
                        newValue.filter { it.isDigit() }
                    }
                    onValueChange(filtered)
                },
                label = { Text(attribute.name) },
                placeholder = { Text(attribute.description.takeIf { it.isNotBlank() } ?: "请输入${attribute.name}") },
                enabled = enabled,
                isError = error != null,
                supportingText = if (error != null) {
                    { Text(error, color = MaterialTheme.colorScheme.error) }
                } else if (attribute.description.isNotBlank() && error == null) {
                    { Text("${attribute.description} (${attribute.minValue} - ${attribute.maxValue})") }
                } else {
                    { Text("范围: ${attribute.minValue} - ${attribute.maxValue}") }
                },
                modifier = Modifier.weight(1f),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = if (attribute.allowDecimal) KeyboardType.Decimal else KeyboardType.Number
                ),
                suffix = if (attribute.unit.isNotBlank()) {
                    { Text(attribute.unit) }
                } else null
            )
        }
    }
}

@UiModePreview
@Composable
private fun NumberRecordFieldPreview() {
    AppTheme {
        NumberRecordField(
            attribute = NumberRoomAttributeInfo(
                name = "Sample Number",
                description = "A sample number field",
                required = false,
                defaultValue = "",
                minValue = 0f,
                maxValue = 100f,
                allowDecimal = true,
                unit = "%",
                sortOrder = 0
            ),
            value = "",
            onValueChange = {}
        )
    }
}
