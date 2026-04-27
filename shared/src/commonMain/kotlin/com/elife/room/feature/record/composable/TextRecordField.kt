package com.elife.room.feature.record.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elife.room.core.ui.theme.AppTheme
import com.elife.room.core.ui.theme.spacing
import com.elife.room.core.ui.tooling.UiModePreview
import com.elife.room.entity.TextRoomAttributeInfo

/**
 * 文本类型记录字段
 * @author Lian
 * @date 2026/4/4
 */
@Composable
fun TextRecordField(
    attribute: TextRoomAttributeInfo,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    error: String? = null
) {
    val valueState = rememberTextFieldState()
    LaunchedEffect(value) {
        valueState.setTextAndPlaceCursorAtEnd(value)
    }

    Column(modifier = modifier) {
        OutlinedTextField(
            state = valueState,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = attribute.name,
                    style = MaterialTheme.typography.bodySmall
                )
            },
            placeholder = {
                Text(
                    text = attribute.description.takeIf { it.isNotBlank() }
                        ?: "请输入${attribute.name}",
                    style = MaterialTheme.typography.bodySmall
                )
            },
            isError = error != null,
            supportingText = null,
            lineLimits = TextFieldLineLimits.SingleLine,
            contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(top = 0.dp, bottom = 0.dp)
        )
    }
}

@UiModePreview
@Composable
private fun TextRecordFieldPreview() {
    AppTheme {
        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                .padding(MaterialTheme.spacing.small)
        ) {
            TextRecordField(
                attribute = TextRoomAttributeInfo(
                    name = "Sample Text",
                    description = "A sample text field",
                    required = false,
                    defaultValue = "",
                    sortOrder = 0
                ),
                value = "",
                onValueChange = {}
            )
        }
    }
}
