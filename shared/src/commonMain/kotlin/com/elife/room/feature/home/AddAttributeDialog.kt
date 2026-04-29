package com.elife.room.feature.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.elife.room.core.ext.isCompact
import com.elife.room.core.ui.composable.AdaptiveDialog
import com.elife.room.core.ui.composable.CommonDialogTitle
import com.elife.room.core.ui.theme.spacing
import com.elife.room.entity.constants.RoomAttributeType
import com.elife.room.shared.Res
import com.elife.room.shared.room_config_add_attribute
import org.jetbrains.compose.resources.stringResource

/**
 * 新增属性对话框
 * @date 2026/4/3
 */
@Composable
fun AddAttributeDialog(
    onDismissRequest: () -> Unit,
    onConfirmClick: (RoomAttributeType) -> Unit,
    modifier: Modifier = Modifier,
    windowInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfoV2()
) {
    AdaptiveDialog(
        onDismissRequest = onDismissRequest,
        content = {
            Column(
                modifier = modifier.then(
                    Modifier.fillMaxWidth(if (windowInfo.isCompact()) 1F else 0.5F)
                        .fillMaxHeight(0.7F)
                ),
            ) {
                CommonDialogTitle(
                    title = stringResource(Res.string.room_config_add_attribute),
                    onCancelClick = onDismissRequest
                )

                LazyColumn(
                    modifier = Modifier.fillMaxWidth().weight(1F)
                        .padding(MaterialTheme.spacing.medium)
                ) {
                    items(RoomAttributeType.entries) { attributeType ->
                        Text(
                            text = attributeType.type,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.fillMaxWidth()
                                .clip(MaterialTheme.shapes.small)
                                .clickable {
                                    onConfirmClick(attributeType)
                                    onDismissRequest()
                                }
                                .padding(MaterialTheme.spacing.medium)
                        )
                    }
                }
            }
        }
    )
}
