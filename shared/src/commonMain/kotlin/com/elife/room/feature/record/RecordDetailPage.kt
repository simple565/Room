package com.elife.room.feature.record

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import com.elife.room.core.ext.defaultId
import com.elife.room.core.ui.composable.RoomTopAppBar
import com.elife.room.core.ui.theme.spacing
import com.elife.room.entity.DatetimeRoomAttributeInfo
import com.elife.room.entity.DurationRoomAttributeInfo
import com.elife.room.entity.ImageRoomAttributeInfo
import com.elife.room.entity.LongTextRoomAttributeInfo
import com.elife.room.entity.MultiSelectRoomAttributeInfo
import com.elife.room.entity.NumberRoomAttributeInfo
import com.elife.room.entity.RatingRoomAttributeInfo
import com.elife.room.entity.RoomAttributeInfo
import com.elife.room.entity.SelectRoomAttributeInfo
import com.elife.room.entity.SwitchRoomAttributeInfo
import com.elife.room.entity.TextRoomAttributeInfo
import com.elife.room.feature.record.composable.DatetimeRecordField
import com.elife.room.feature.record.composable.DurationRecordField
import com.elife.room.feature.record.composable.ImageRecordField
import com.elife.room.feature.record.composable.LongTextRecordField
import com.elife.room.feature.record.composable.MultiSelectRecordField
import com.elife.room.feature.record.composable.NumberRecordField
import com.elife.room.feature.record.composable.RatingRecordField
import com.elife.room.feature.record.composable.SelectRecordField
import com.elife.room.feature.record.composable.SwitchRecordField
import com.elife.room.feature.record.composable.TextRecordField
import com.elife.room.shared.Res
import com.elife.room.shared.record_add
import com.elife.room.shared.record_edit
import com.elife.room.shared.save
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

/**
 * 记录编辑页面
 * @author Lian
 * @date 2026/4/4
 */
/**
 * 编辑记录页面
 */
@Serializable
data class RecordDetailRoute(val roomId: Long, val recordId: Long) : NavKey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordDetailPage(
    roomId: Long,
    recordId: Long,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecordEditViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(roomId, recordId) {
        viewModel.loadRecord(roomId, recordId)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            RoomTopAppBar(
                title = stringResource(if (recordId == Long.defaultId) Res.string.record_add else Res.string.record_edit),
                onBackClick = onBackClick,
                actions = {
                    TextButton(
                        onClick = { viewModel.saveRecord() },
                        content = {
                            Text(
                                text = stringResource(Res.string.save),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    )
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(MaterialTheme.spacing.medium),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
            ) {
                items(items = uiState.recordInfoList, key = { it.attribute.id }) { recordInfo ->
                    RecordFieldComposable(
                        attribute = recordInfo.attribute,
                        value = recordInfo.value,
                        onValueChange = { viewModel.updateFieldValue(recordInfo.attribute.id, it) }
                    )
                }
            }

            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            if (uiState.saved) {
                onBackClick()
            }
        }
    }
}


/**
 * 表单字段 Composable 基础接口
 */
@Composable
fun RecordFieldComposable(
    attribute: RoomAttributeInfo,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    error: String? = null
) {
    when (attribute) {
        is TextRoomAttributeInfo -> {
            TextRecordField(
                attribute = attribute,
                value = value,
                onValueChange = onValueChange,
                modifier = modifier,
                error = error
            )
        }
        is LongTextRoomAttributeInfo -> {
            LongTextRecordField(
                attribute = attribute,
                value = value,
                onValueChange = onValueChange,
                modifier = modifier,
                enabled = enabled,
                error = error
            )
        }
        is NumberRoomAttributeInfo -> {
            NumberRecordField(
                attribute = attribute,
                value = value,
                onValueChange = onValueChange,
                modifier = modifier,
                enabled = enabled,
                error = error
            )
        }
        is RatingRoomAttributeInfo -> {
            RatingRecordField(
                attribute = attribute,
                value = value,
                onValueChange = onValueChange,
                modifier = modifier,
                enabled = enabled
            )
        }
        is DatetimeRoomAttributeInfo -> {
            DatetimeRecordField(
                attribute = attribute,
                value = value,
                onValueChange = onValueChange,
                modifier = modifier,
                enabled = enabled
            )
        }
        is SelectRoomAttributeInfo -> {
            SelectRecordField(
                attribute = attribute,
                value = value,
                onValueChange = onValueChange,
                modifier = modifier,
                enabled = enabled
            )
        }
        is MultiSelectRoomAttributeInfo -> {
            MultiSelectRecordField(
                attribute = attribute,
                value = value,
                onValueChange = onValueChange,
                modifier = modifier
            )
        }
        is SwitchRoomAttributeInfo -> {
            SwitchRecordField(
                attribute = attribute,
                value = value,
                onValueChange = onValueChange,
                modifier = modifier,
                enabled = enabled
            )
        }
        is ImageRoomAttributeInfo -> {
            ImageRecordField(
                attribute = attribute,
                value = value,
                onValueChange = onValueChange,
                modifier = modifier,
                enabled = enabled
            )
        }
        is DurationRoomAttributeInfo -> {
            DurationRecordField(
                attribute = attribute,
                value = value,
                onValueChange = onValueChange,
                modifier = modifier,
                enabled = enabled
            )
        }
        else -> {
            TextRecordField(
                attribute = attribute as TextRoomAttributeInfo,
                value = value,
                onValueChange = onValueChange,
                modifier = modifier,
                error = error
            )
        }
    }
}