package com.elife.room.core.ui.tooling

import androidx.compose.ui.tooling.preview.AndroidUiModes
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview


/**
 * @author lianml
 * @date 2025/11/7
 */
@Preview(device = Devices.PHONE)
@Preview(device = Devices.FOLDABLE)
@Preview(device = Devices.TABLET)
annotation class DevicePreview

@Preview(uiMode = AndroidUiModes.UI_MODE_NIGHT_YES)
@Preview(uiMode = AndroidUiModes.UI_MODE_NIGHT_NO)
annotation class UiModePreview