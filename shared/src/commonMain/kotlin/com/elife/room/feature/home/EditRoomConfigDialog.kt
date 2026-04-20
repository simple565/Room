package com.elife.room.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.DragHandle
import androidx.compose.material.icons.rounded.RemoveCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.elife.room.core.ext.isCompact
import com.elife.room.core.ext.isDefaultId
import com.elife.room.core.ui.composable.AdaptiveDialog
import com.elife.room.core.ui.composable.CommonDialogTitle
import com.elife.room.core.ui.composable.InputTextField
import com.elife.room.core.ui.composable.TextWithRequiredMark
import com.elife.room.core.ui.theme.AppTheme
import com.elife.room.core.ui.theme.iconSize
import com.elife.room.core.ui.theme.spacing
import com.elife.room.core.ui.tooling.UiModePreview
import com.elife.room.entity.RoomAttributeInfo
import com.elife.room.entity.RoomInfo
import com.elife.room.shared.Res
import com.elife.room.shared.room_config_add
import com.elife.room.shared.room_config_add_attribute
import com.elife.room.shared.room_config_edit
import com.elife.room.shared.room_config_name
import com.elife.room.shared.room_config_support_add_by_search
import com.elife.room.shared.room_config_support_create_list
import com.elife.room.shared.room_config_support_quick_add
import com.elife.room.shared.room_config_title_attribute
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

/**
 * 编辑房间配置对话框
 * @date 2025/12/8
 */
@Composable
fun EditRoomConfigDialog(
    roomId: Long,
    onDismissRequest: (RoomInfo?) -> Unit,
    modifier: Modifier = Modifier,
    windowInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
    viewModel: EditRoomViewModel = koinViewModel()
) {
    LaunchedEffect(roomId) {
        viewModel.loadRoomInfo(roomId)
    }

    val roomInfo = viewModel.roomInfo.collectAsStateWithLifecycle()

    EditRoomConfigDialog(
        roomInfo = roomInfo.value,
        onDismissRequest = { onDismissRequest(null) },
        onConfirmClick = {
            viewModel.saveRoom(it)
            onDismissRequest(it)
        },
        modifier = modifier,
        windowInfo = windowInfo
    )
}

@Composable
fun EditRoomConfigDialog(
    templateFileName: String,
    onDismissRequest: (RoomInfo?) -> Unit,
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
        onDismissRequest = { onDismissRequest(null) },
        onConfirmClick = {
            viewModel.saveRoom(it)
            onDismissRequest(it)
        },
        modifier = modifier,
        windowInfo = windowInfo
    )
}


@Composable
fun EditRoomConfigDialog(
    roomInfo: RoomInfo,
    onDismissRequest: () -> Unit,
    onConfirmClick: (RoomInfo) -> Unit,
    modifier: Modifier = Modifier,
    windowInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo()
) {
    val attributesState = remember { mutableStateOf(roomInfo.attributeInfoList) }
    val showAddAttrDialog = remember { mutableStateOf(false)}

    LaunchedEffect(roomInfo.attributeInfoList) {
        attributesState.value = roomInfo.attributeInfoList
    }

    val switchRowModifier = Modifier.fillMaxWidth().wrapContentHeight()
        .background(
            color = MaterialTheme.colorScheme.surface,
            shape = MaterialTheme.shapes.medium
        )
        .padding(MaterialTheme.spacing.small)

    AdaptiveDialog(
        onDismissRequest = onDismissRequest,
        content = {
            Column(
                modifier = modifier.then(
                    Modifier.fillMaxWidth(if (windowInfo.isCompact()) 1F else 0.7F)
                        .fillMaxHeight(if (windowInfo.isCompact()) 0.85F else 0.55F)
                ),
            ) {
                CommonDialogTitle(
                    title = stringResource(if (roomInfo.id.isDefaultId) Res.string.room_config_add else Res.string.room_config_edit),
                    onCancelClick = onDismissRequest,
                    onConfirmClick = { onConfirmClick(roomInfo.copy(attributeInfoList = attributesState.value)) }
                )

                LazyColumn(
                    modifier = Modifier.fillMaxWidth().scrollable(
                        state = rememberScrollState(),
                        orientation = Orientation.Vertical
                    ).padding(MaterialTheme.spacing.medium),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
                ) {
                    item {
                        RoomNameRow(
                            name = roomInfo.name,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item {
                        SwitchAttributeRow(
                            name = stringResource(Res.string.room_config_support_quick_add),
                            value = roomInfo.supportQuickAdd,
                            onValueChange = {},
                            modifier = switchRowModifier
                        )
                    }
                    item {
                        Text(
                            text = stringResource(Res.string.room_config_title_attribute),
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                    item {
                        CustomAttributeColumn(
                            attributes = attributesState.value,
                            onAttributeAdd = { showAddAttrDialog.value = true },
                            onAttributeRemove = { attributesState.value = attributesState.value.minus(it) },
                            modifier = Modifier.fillMaxWidth().weight(1F)
                                .background(
                                    color = MaterialTheme.colorScheme.surface,
                                    shape = MaterialTheme.shapes.medium
                                ),
                        )
                    }
                    item {
                        SwitchAttributeRow(
                            name = stringResource(Res.string.room_config_support_create_list),
                            value = roomInfo.supportCreateList,
                            onValueChange = {},
                            modifier = switchRowModifier
                        )
                    }
                    item {
                        SwitchAttributeRow(
                            name = stringResource(Res.string.room_config_support_add_by_search),
                            value = roomInfo.supportAddBySearch,
                            onValueChange = {},
                            modifier = switchRowModifier
                        )
                    }
                }
            }
        }
    )

    if (showAddAttrDialog.value) {
        AddAttributeDialog(
            onDismissRequest = { showAddAttrDialog.value = false },
            onConfirmClick = {
            },
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            windowInfo = windowInfo
        )
    }
}

@Composable
private fun CustomAttributeColumn(
    attributes: List<RoomAttributeInfo>,
    onAttributeAdd: () -> Unit,
    onAttributeRemove: (RoomAttributeInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    val itemModifier = Modifier.fillMaxWidth()
        .padding(
            horizontal = MaterialTheme.spacing.small,
            vertical = MaterialTheme.spacing.medium
        )

    Column(modifier) {
        attributes.forEach {
            key(it.name, it.type) {
                CustomAttributeRow(
                    name = it.name,
                    onAttributeRemove = { onAttributeRemove(it) },
                    modifier = itemModifier
                )
            }
        }
        Row(
            modifier = itemModifier.clickable(
                indication = null,
                interactionSource = null,
                onClick = onAttributeAdd
            ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = stringResource(Res.string.room_config_add),
                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small)
            )
            Text(
                text = stringResource(Res.string.room_config_add_attribute),
                style = MaterialTheme.typography.bodySmall
            )
        }
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
private fun CustomAttributeRow(
    name: String,
    onAttributeRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.then(
            Modifier.fillMaxWidth().wrapContentHeight()
        ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onAttributeRemove,
            modifier = Modifier.size(MaterialTheme.iconSize.large),
            content = {
                Icon(
                    imageVector = Icons.Rounded.RemoveCircle,
                    contentDescription = null,
                    modifier = Modifier.size(MaterialTheme.iconSize.medium),
                    tint = MaterialTheme.colorScheme.error
                )
            }
        )
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            lineHeight = MaterialTheme.typography.bodyMedium.fontSize,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small)
        )
        Spacer(modifier = Modifier.weight(1F))
        Icon(
            imageVector = Icons.Rounded.DragHandle,
            contentDescription = null,
            modifier = Modifier.width(MaterialTheme.iconSize.large),
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun SwitchAttributeRow(
    name: String,
    value: Boolean,
    onValueChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.then(
            Modifier.fillMaxWidth().wrapContentHeight()
        ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Switch(
            checked = value,
            onCheckedChange = onValueChange,
            modifier = Modifier.scale(0.75F)
        )
    }
}

@UiModePreview
@Composable
private fun AttributeRowPreview() {
    AppTheme {
        Column {
            SwitchAttributeRow(
                name = "属性名称",
                value = true,
                onValueChange = {},
                modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface)
            )
            CustomAttributeRow(
                name = "属性名称",
                onAttributeRemove = {},
                modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface)
            )
        }
    }
}