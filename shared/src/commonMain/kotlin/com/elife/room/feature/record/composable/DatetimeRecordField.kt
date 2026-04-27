package com.elife.room.feature.record.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.elife.room.core.ui.theme.AppTheme
import com.elife.room.core.ui.tooling.UiModePreview
import com.elife.room.entity.DatetimeRoomAttributeInfo
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

/**
 * 日期时间类型记录字段
 * @author Lian
 * @date 2026/4/4
 */
@Composable
fun DatetimeRecordField(
    attribute: DatetimeRoomAttributeInfo,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    // 解析时间戳
    val timestamp = value.toLongOrNull() ?: Clock.System.now().toEpochMilliseconds()
    val displayText = try {
        val instant = Instant.fromEpochMilliseconds(timestamp)
        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        when (attribute.format) {
            "yyyy-MM-dd" -> "${localDateTime.year}-${localDateTime.monthNumber.toString().padStart(2, '0')}-${localDateTime.dayOfMonth.toString().padStart(2, '0')}"
            "HH:mm:ss" -> "${localDateTime.hour.toString().padStart(2, '0')}:${localDateTime.minute.toString().padStart(2, '0')}:${localDateTime.second.toString().padStart(2, '0')}"
            else -> "${localDateTime.year}-${localDateTime.monthNumber.toString().padStart(2, '0')}-${localDateTime.dayOfMonth.toString().padStart(2, '0')} ${localDateTime.hour.toString().padStart(2, '0')}:${localDateTime.minute.toString().padStart(2, '0')}:${localDateTime.second.toString().padStart(2, '0')}"
        }
    } catch (e: Exception) {
        "选择日期时间"
    }

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
        OutlinedButton(
            onClick = {
                // TODO: 打开日期时间选择器
                // 暂时使用当前时间
                if (value.isBlank()) {
                    onValueChange(Clock.System.now().toEpochMilliseconds().toString())
                }
            },
            enabled = enabled,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(displayText)
        }
    }
}

@UiModePreview
@Composable
private fun DatetimeRecordFieldPreview() {
    AppTheme {
        DatetimeRecordField(
            attribute = DatetimeRoomAttributeInfo(
                name = "Sample Date",
                description = "A sample date field",
                required = false,
                defaultValue = "",
                format = "yyyy-MM-dd",
                sortOrder = 0
            ),
            value = "",
            onValueChange = {}
        )
    }
}
