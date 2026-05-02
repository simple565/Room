package com.elife.room.core.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.elife.room.core.ext.isCompact
import com.elife.room.core.ui.theme.AppTheme
import com.elife.room.core.ui.tooling.DevicePreview

/**
 * 自适应对话框（兼容对话框和底部弹窗）
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdaptiveDialog(
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit = {},
    usePlatformDefaultWidth: Boolean = false,
    dismissOnBackPress: Boolean = true,
    dismissOnClickOutside: Boolean = true,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfoV2()
) {
    if (windowAdaptiveInfo.isCompact()) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            modifier = Modifier,
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            sheetGesturesEnabled = false,
            shape = MaterialTheme.shapes.large.copy(
                bottomStart = ZeroCornerSize,
                bottomEnd = ZeroCornerSize
            ),
            dragHandle = null,
            properties = ModalBottomSheetProperties(
                shouldDismissOnBackPress = dismissOnBackPress,
                shouldDismissOnClickOutside = dismissOnClickOutside
            )
        ) {
            content.invoke()
        }
    } else {
        Dialog(
            onDismissRequest = onDismissRequest,
            properties = DialogProperties(
                usePlatformDefaultWidth = usePlatformDefaultWidth,
                dismissOnBackPress = dismissOnBackPress,
                dismissOnClickOutside = dismissOnBackPress
            )
        ) {
            Surface(
                color = MaterialTheme.colorScheme.surfaceContainerLow,
                shape = MaterialTheme.shapes.large,
                tonalElevation = 6.dp
            ) {
                content.invoke()
            }
        }
    }
}

@DevicePreview
@Composable
fun AdaptiveDialog() {
    AppTheme {
        AdaptiveDialog(
            onDismissRequest = {},
            content = {
                Column {
                    CommonDialogTitle(
                        title = "标题",
                        onCancelClick = {},
                        onConfirmClick = { }
                    )
                    Text(
                        text = "Hello World"
                    )
                }
            }
        )
    }
}