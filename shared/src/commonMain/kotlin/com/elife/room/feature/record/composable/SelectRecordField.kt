package com.elife.room.feature.record.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.elife.room.core.ui.composable.DropdownSelector
import com.elife.room.core.ui.theme.AppTheme
import com.elife.room.core.ui.tooling.UiModePreview
import com.elife.room.entity.SelectRoomAttributeInfo
import com.elife.room.shared.Res
import com.elife.room.shared.prompt_select_format
import org.jetbrains.compose.resources.stringResource

/**
 * 单选类型记录字段
 * @author Lian
 * @date 2026/4/4
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectRecordField(
    attribute: SelectRoomAttributeInfo,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    var expanded by remember { mutableStateOf(false) }
    val options = attribute.options

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
        DropdownSelector(
            label = stringResource(Res.string.prompt_select_format, attribute.name),
            isRequired = attribute.required,
            selected = value,
            options = options,
            onSelected = onValueChange,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@UiModePreview
@Composable
private fun SelectRecordFieldPreview() {
    AppTheme {
        Column(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
            SelectRecordField(
                attribute = SelectRoomAttributeInfo(
                    name = "Sample",
                    description = "A sample select field",
                    required = false,
                    defaultValue = "Option 1",
                    options = listOf("Option 1", "Option 2", "Option 3"),
                    sortOrder = 0
                ),
                value = "",
                onValueChange = {}
            )
        }
    }
}
