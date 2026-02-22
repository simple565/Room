package com.elife.room.core.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.elife.room.core.ext.isCompact

/**
 * 自适应对话框（兼容对话框和底部弹窗）
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdaptiveDialog(
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit = {},
    dismissOnBackPress: Boolean = true,
    dismissOnClickOutside: Boolean = true,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo()
) {
    if (windowAdaptiveInfo.isCompact()) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            modifier = Modifier,
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            sheetGesturesEnabled = false,
            shape = MaterialTheme.shapes.medium.copy(
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
                usePlatformDefaultWidth = false,
                dismissOnBackPress = dismissOnBackPress,
                dismissOnClickOutside = dismissOnBackPress
            )
        ) {
            Box(
                modifier = Modifier.wrapContentSize().background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shapes.medium
                )
            ) {
                content.invoke()
            }
        }
    }
}