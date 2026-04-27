package com.elife.room.core.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.elife.room.core.ui.theme.AppTheme
import com.elife.room.core.ui.theme.iconSize
import com.elife.room.core.ui.theme.spacing
import com.elife.room.core.ui.tooling.UiModePreview
import com.elife.room.shared.Res
import com.elife.room.shared.add
import org.jetbrains.compose.resources.stringResource

@Composable
fun ActionChip(
    label: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.then(
            Modifier.defaultMinSize(minWidth = 72.dp, minHeight = 32.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainerLow,
                    shape = MaterialTheme.shapes.medium
                ).clip(MaterialTheme.shapes.medium)
                .clickable(onClick = onClick)
        ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(MaterialTheme.iconSize.small),
            tint = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = label,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(start = 2.dp)
        )
    }
}

@UiModePreview
@Composable
private fun ActionChipPreview() {
    AppTheme {
        Column(modifier = Modifier.padding(MaterialTheme.spacing.small)) {
            ActionChip(
                label = stringResource(Res.string.add),
                icon = Icons.Rounded.Add,
                onClick = {},
                modifier = Modifier.size(width = 80.dp, height = 32.dp)
            )
        }
    }
}
