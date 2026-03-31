package com.elife.room.feature.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.elife.room.core.ext.defaultId
import com.elife.room.core.ext.isCompact
import com.elife.room.core.ext.isDefaultId
import com.elife.room.core.ui.composable.AdaptiveDialog
import com.elife.room.core.ui.composable.InputTextField
import com.elife.room.core.ui.composable.TextWithRequiredMark
import com.elife.room.core.ui.theme.spacing
import com.elife.room.shared.Res
import com.elife.room.shared.cancel
import com.elife.room.shared.done
import com.elife.room.shared.new
import com.elife.room.shared.room_config_add
import com.elife.room.shared.room_config_edit
import com.elife.room.shared.room_config_name
import com.elife.room.shared.room_config_title_attribute
import com.elife.room.shared.room_config_title_basic
import org.jetbrains.compose.resources.stringResource

/**
 * 编辑房间配置对话框
 * @date 2025/12/8
 */
@Composable
fun EditRoomConfigDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    roomId: Int = Int.defaultId,
    windowInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
    viewModel: EditRoomViewModel = viewModel { EditRoomViewModel() }
) {
    LaunchedEffect(roomId) {
        viewModel.loadRoomConfig(roomId)
    }
    // val roomInfo by viewModel.roomInfoState.collectAsStateWithLifecycle()

    AdaptiveDialog(
        onDismissRequest = onDismissRequest,
        content = {
            LazyColumn(
                modifier = modifier.then(
                    Modifier.fillMaxWidth(if (windowInfo.isCompact()) 1F else 0.7F)
                        .fillMaxHeight(if (windowInfo.isCompact()) 1F else 0.8F)
                ),
                contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.medium),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Title(
                        isNewRoom = roomId.isDefaultId,
                        onCancelClick = onDismissRequest,
                        onConfirmClick = {},
                        modifier = Modifier.fillMaxWidth().wrapContentHeight()
                            .padding(vertical = MaterialTheme.spacing.medium)
                    )
                }
                item {
                    RoomNameRow(
                        name = "roomInfo.name",
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                item {
                    BasicConfigColumn(
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    )
}

@Composable
private fun Title(
    isNewRoom: Boolean,
    onCancelClick: () -> Unit,
    onConfirmClick: () -> Unit,
     modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(Res.string.cancel),
            modifier = Modifier.clickable { onCancelClick() },
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = stringResource(if (isNewRoom) Res.string.room_config_add else Res.string.room_config_edit),
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = stringResource(if (isNewRoom) Res.string.new else Res.string.done),
            modifier = Modifier.clickable { onConfirmClick() },
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary)
        )
    }
}

@Composable
private fun RoomNameRow(name: String, modifier: Modifier = Modifier) {
    val nameState = rememberTextFieldState()
    LaunchedEffect(name) {
        nameState.setTextAndPlaceCursorAtEnd(name)
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        InputTextField(
            state = nameState,
            placeholder = {
                TextWithRequiredMark(
                    text = stringResource(Res.string.room_config_name),
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.outlineVariant)
                )
            },
            lineLimits = TextFieldLineLimits.SingleLine,
        )
    }
}

@Composable
private fun BasicConfigColumn(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(Res.string.room_config_title_basic)
        )
        Column(modifier = Modifier) {

        }
    }
}

@Composable
private fun CustomConfigColumn(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(Res.string.room_config_title_attribute)
        )

    }
}