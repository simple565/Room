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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.elife.room.core.ui.composable.OutlinedTextField
import com.elife.room.core.ui.theme.iconSize
import com.elife.room.core.ui.theme.spacing
import com.elife.room.entity.SearchParameter
import com.elife.room.shared.Res
import com.elife.room.shared.add
import com.elife.room.shared.search_plugin_key
import com.elife.room.shared.search_plugin_value
import org.jetbrains.compose.resources.stringResource

@Composable
fun KeyValueEditor(
    items: List<SearchParameter>,
    onValueChange: (List<SearchParameter>) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
    ) {
        items.forEachIndexed { index, item ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = item.key,
                    onValueChange = { newKey ->
                        onValueChange(items.toMutableList().apply {
                            this[index] = item.copy(key = newKey)
                        })
                    },
                    label = stringResource(Res.string.search_plugin_key),
                    modifier = Modifier.weight(0.3F),
                    singleLine = true
                )
                OutlinedTextField(
                    value = item.value,
                    onValueChange = { newValue ->
                        onValueChange(items.toMutableList().apply {
                            this[index] = item.copy(value = newValue)
                        })
                    },
                    label = stringResource(Res.string.search_plugin_value),
                    modifier = Modifier.weight(0.7F),
                    singleLine = true
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
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = MaterialTheme.spacing.extraSmall),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    onValueChange(items + SearchParameter(key = ""))
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = stringResource(Res.string.add),
                    modifier = Modifier.size(MaterialTheme.iconSize.medium)
                )
            }
        }
    }
}
