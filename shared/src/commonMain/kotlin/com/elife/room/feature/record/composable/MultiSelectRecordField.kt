package com.elife.room.feature.record.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.elife.room.core.ui.composable.ActionChip
import com.elife.room.core.ui.composable.TextWithRequiredMark
import com.elife.room.core.ui.theme.AppTheme
import com.elife.room.core.ui.theme.spacing
import com.elife.room.core.ui.tooling.UiModePreview
import com.elife.room.entity.MultiSelectRoomAttributeInfo
import com.elife.room.entity.ext.globalJson
import com.elife.room.shared.Res
import com.elife.room.shared.add
import org.jetbrains.compose.resources.stringResource

/**
 * 多选类型记录字段
 * @author Lian
 * @date 2026/4/4
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MultiSelectRecordField(
    attribute: MultiSelectRoomAttributeInfo,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val optionListState = remember { mutableStateOf<List<String>>(emptyList()) }
    LaunchedEffect(attribute.options) {
        optionListState.value = try {
            globalJson.decodeFromString<List<String>>(value)
        } catch (e: Exception) {
            emptyList()
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        TextWithRequiredMark(
            text = attribute.name,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )
        FlowRow(
            modifier = Modifier.fillMaxWidth().padding(top = MaterialTheme.spacing.small),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
        ) {
            val chipModifier = Modifier.size(width = 72.dp, height = 32.dp)
            attribute.options.forEach { option ->
                val isSelected = optionListState.value.contains(option)
                FilterChip(
                    selected = isSelected,
                    onClick = {
                        val newSelection = if (isSelected) {
                            optionListState.value - option
                        } else {
                            optionListState.value + option
                        }
                        onValueChange(globalJson.encodeToString(newSelection))
                    },
                    label = {
                        Text(
                            text = option,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center)
                        )
                    },
                    shape = MaterialTheme.shapes.medium,
                    modifier = chipModifier
                )
            }
            ActionChip(
                label = stringResource(Res.string.add),
                icon = Icons.Rounded.Add,
                onClick = {},
                modifier = chipModifier
            )
        }
    }
}

@UiModePreview
@Composable
private fun MultiSelectRecordFieldPreview() {
    AppTheme {
        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                .padding(MaterialTheme.spacing.small)
        ) {
            MultiSelectRecordField(
                attribute = MultiSelectRoomAttributeInfo(
                    name = "标签",
                    description = "A sample multi select field",
                    required = false,
                    defaultValue = "",
                    options = listOf("选项1", "选项2", "选项3", "选项4", "选项5"),
                    sortOrder = 0
                ),
                value = "[\"选项3\", \"选项5\"]",
                onValueChange = {}
            )
        }
    }
}
