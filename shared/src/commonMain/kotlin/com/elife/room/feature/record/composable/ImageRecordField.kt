package com.elife.room.feature.record.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elife.room.core.ui.theme.AppTheme
import com.elife.room.core.ui.tooling.UiModePreview
import com.elife.room.entity.ImageRoomAttributeInfo

/**
 * 图片类型记录字段
 * @author Lian
 * @date 2026/4/4
 */
@Composable
fun ImageRecordField(
    attribute: ImageRoomAttributeInfo,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
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

        if (value.isNotBlank()) {
            // 显示已选择的图片
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(top = 8.dp)
            ) {
                // TODO: 使用 Coil 加载图片
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("图片: $value")
                }
            }
        }

        OutlinedButton(
            onClick = {
                // TODO: 打开图片选择器
            },
            enabled = enabled,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Image,
                contentDescription = null,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(if (value.isBlank()) "选择图片" else "更换图片")
        }
    }
}

@UiModePreview
@Composable
private fun ImageRecordFieldPreview() {
    AppTheme {
        ImageRecordField(
            attribute = ImageRoomAttributeInfo(
                name = "Sample Image",
                description = "A sample image field",
                required = false,
                defaultValue = "",
                sortOrder = 0
            ),
            value = "",
            onValueChange = {}
        )
    }
}
