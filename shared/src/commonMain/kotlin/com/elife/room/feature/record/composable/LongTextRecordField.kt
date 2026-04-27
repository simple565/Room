package com.elife.room.feature.record.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.elife.room.core.ui.theme.AppTheme
import com.elife.room.core.ui.tooling.UiModePreview
import com.elife.room.entity.LongTextRoomAttributeInfo

/**
 * 长文本类型记录字段
 * @author Lian
 * @date 2026/4/4
 */
@Composable
fun LongTextRecordField(
    attribute: LongTextRoomAttributeInfo,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    error: String? = null
) {
    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(attribute.name) },
            placeholder = { Text(attribute.description.takeIf { it.isNotBlank() } ?: "请输入${attribute.name}") },
            enabled = enabled,
            isError = error != null,
            supportingText = if (error != null) {
                { Text(error, color = MaterialTheme.colorScheme.error) }
            } else if (attribute.description.isNotBlank() && error == null) {
                { Text(attribute.description) }
            } else null,
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            maxLines = 6
        )
    }
}

@UiModePreview
@Composable
private fun LongTextRecordFieldPreview() {
    AppTheme {
        LongTextRecordField(
            attribute = LongTextRoomAttributeInfo(
                name = "Sample Long Text",
                description = "A sample long text field",
                required = false,
                defaultValue = "",
                sortOrder = 0
            ),
            value = "",
            onValueChange = {}
        )
    }
}
