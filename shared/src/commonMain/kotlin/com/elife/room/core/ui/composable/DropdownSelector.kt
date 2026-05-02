package com.elife.room.core.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import com.elife.room.core.ui.theme.AppTheme
import com.elife.room.core.ui.theme.spacing
import com.elife.room.core.ui.tooling.UiModePreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownSelector(
    selected: String,
    options: List<String>,
    onSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    isRequired: Boolean = false
) {
    val expandState = remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expandState.value,
        onExpandedChange = { expandState.value = it },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selected,
            onValueChange = {},
            modifier = Modifier.fillMaxWidth()
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
            readOnly = true,
            label = label,
            isRequired = isRequired,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Rounded.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.rotate(if (expandState.value) 180F else 0F)
                )
            },
            singleLine = true,
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
        )
        ExposedDropdownMenu(
            expanded = expandState.value,
            onDismissRequest = { expandState.value = false }
        ) {
            options.forEach {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    onClick = {
                        onSelected(it)
                        expandState.value = false
                    },
                    modifier = Modifier.clip(MaterialTheme.shapes.small)
                )
            }
        }
    }
}

@UiModePreview
@Composable
private fun RequestMethodSelectorPreview() {
    AppTheme {
        Row(
            modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
        ) {
            DropdownSelector(
                selected = "1",
                options = listOf("1", "2"),
                onSelected = {},
                modifier = Modifier.weight(0.3F)
            )
            DropdownSelector(
                selected = "1",
                options = listOf("1", "2"),
                onSelected = {},
                modifier = Modifier.weight(0.3F)
            )
            DropdownSelector(
                selected = "1",
                options = listOf("1", "2"),
                onSelected = {},
                modifier = Modifier.weight(0.3F)
            )
        }
    }
}