package com.elife.room.feature.search.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.RemoveCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.elife.room.core.ui.composable.DropdownSelector
import com.elife.room.core.ui.composable.OutlinedTextField
import com.elife.room.core.ui.theme.iconSize
import com.elife.room.core.ui.theme.spacing
import com.elife.room.entity.ResponseMapping
import com.elife.room.entity.constants.RoomAttributeType
import com.elife.room.shared.Res
import com.elife.room.shared.search_plugin_mapping_add
import com.elife.room.shared.search_plugin_mapping_name
import com.elife.room.shared.search_plugin_mapping_regex
import com.elife.room.shared.search_plugin_mapping_value
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResponseMappingEditor(
    items: List<ResponseMapping>,
    onValueChange: (List<ResponseMapping>) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        items.forEachIndexed { index, item ->
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.name.ifBlank { "${stringResource(Res.string.search_plugin_mapping_name)} ${index + 1}" },
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    IconButton(
                        onClick = {
                            onValueChange(items.toMutableList().apply { removeAt(index) })
                        },
                        modifier = Modifier.size(MaterialTheme.iconSize.large)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.RemoveCircle,
                            contentDescription = null,
                            modifier = Modifier.size(MaterialTheme.iconSize.medium),
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
                DropdownSelector(
                    selected = if (item.type.isBlank()) "" else RoomAttributeType.typeOf(item.type).name,
                    options = listOf("") + RoomAttributeType.entries.map { it.name},
                    onSelected = {},
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = item.name,
                    onValueChange = { newName ->
                        onValueChange(items.toMutableList().apply {
                            this[index] = item.copy(name = newName)
                        })
                    },
                    label = stringResource(Res.string.search_plugin_mapping_name),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = item.regex,
                    onValueChange = { newRegex ->
                        onValueChange(items.toMutableList().apply {
                            this[index] = item.copy(regex = newRegex)
                        })
                    },
                    label = stringResource(Res.string.search_plugin_mapping_regex),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = item.value,
                    onValueChange = { newValue ->
                        onValueChange(items.toMutableList().apply {
                            this[index] = item.copy(value = newValue)
                        })
                    },
                    label = stringResource(Res.string.search_plugin_mapping_value),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = MaterialTheme.spacing.extraSmall),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    onValueChange(
                        items + ResponseMapping(
                            type = "",
                            name = "",
                            regex = "",
                            value = ""
                        )
                    )
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = stringResource(Res.string.search_plugin_mapping_add),
                    modifier = Modifier.size(MaterialTheme.iconSize.medium)
                )
            }
            Text(
                text = stringResource(Res.string.search_plugin_mapping_add),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
