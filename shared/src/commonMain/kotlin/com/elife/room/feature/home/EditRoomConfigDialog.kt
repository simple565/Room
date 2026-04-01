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
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.draw.clip
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.elife.room.core.ext.defaultId
import com.elife.room.core.ext.isCompact
import com.elife.room.core.ext.isDefaultId
import com.elife.room.core.ui.composable.AdaptiveDialog
import com.elife.room.core.ui.composable.InputTextField
import com.elife.room.core.ui.composable.TextWithRequiredMark
import com.elife.room.core.ui.theme.spacing
import com.elife.room.entity.RoomInfo
import com.elife.room.shared.Res
import com.elife.room.shared.cancel
import com.elife.room.shared.done
import com.elife.room.shared.new
import com.elife.room.shared.room_config_add
import com.elife.room.shared.room_config_edit
import com.elife.room.shared.room_config_name
import com.elife.room.shared.room_config_title_attribute
import com.elife.room.shared.room_config_title_basic
import io.ktor.util.Hash
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

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
    viewModel: EditRoomViewModel = koinViewModel()
) {
}

@Composable
fun EditRoomConfigDialog(
    templateFileName: String,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    windowInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
    viewModel: EditRoomViewModel = koinViewModel()
) {
    LaunchedEffect(templateFileName) {
        viewModel.loadRoomInfo(templateFileName)
    }

    val roomInfo = viewModel.roomInfo.collectAsStateWithLifecycle()

    EditRoomConfigDialog(
        roomInfo = roomInfo.value,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        windowInfo = windowInfo
    )
}


@Composable
fun EditRoomConfigDialog(
    roomInfo: RoomInfo,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    windowInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo()
) {
    AdaptiveDialog(
        onDismissRequest = onDismissRequest,
        content = {
            Column(
                modifier = modifier.then(
                    Modifier.fillMaxWidth(if (windowInfo.isCompact()) 1F else 0.7F)
                        .fillMaxHeight(if (windowInfo.isCompact()) 0.85F else 0.55F)
                        .padding(horizontal = MaterialTheme.spacing.small)
                ),
            ) {
                Title(
                    isNewRoom = roomInfo.id.isDefaultId,
                    onCancelClick = onDismissRequest,
                    onConfirmClick = {},
                    modifier = Modifier.fillMaxWidth().wrapContentHeight()
                        .padding(MaterialTheme.spacing.medium)
                )
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().wrapContentHeight().clip(MaterialTheme.shapes.small),
                    contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.medium),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Text("属性")
                    }
                    items(items = roomInfo.attributes, key = { Hash.combine(it.type, it.name) }) {
                        RoomNameRow(
                            name = it.name,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
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