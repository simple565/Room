package com.elife.room.feature.record.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elife.room.core.ui.theme.AppTheme
import com.elife.room.core.ui.tooling.UiModePreview
import com.elife.room.entity.RatingRoomAttributeInfo

/**
 * 评分类型记录字段
 * @author Lian
 * @date 2026/4/4
 */
@Composable
fun RatingRecordField(
    attribute: RatingRoomAttributeInfo,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val currentRating = value.toIntOrNull() ?: 0
    val maxRating = attribute.maxValue.coerceIn(1, 10)

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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            for (i in 1..maxRating) {
                IconButton(
                    onClick = {
                        if (enabled) {
                            onValueChange(i.toString())
                        }
                    },
                    enabled = enabled,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = if (i <= currentRating) {
                            Icons.Filled.Star
                        } else {
                            Icons.Outlined.StarOutline
                        },
                        contentDescription = "$i 星",
                        tint = if (i <= currentRating) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.outline
                        },
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "$currentRating / $maxRating",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@UiModePreview
@Composable
private fun RatingRecordFieldPreview() {
    AppTheme {
        RatingRecordField(
            attribute = RatingRoomAttributeInfo(
                name = "Sample Rating",
                description = "A sample rating field",
                required = false,
                defaultValue = "",
                maxValue = 5,
                sortOrder = 0
            ),
            value = "3",
            onValueChange = {}
        )
    }
}
