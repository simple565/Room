package com.elife.room.feature.record

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elife.room.core.ext.defaultId
import com.elife.room.entity.RecordInfo
import com.elife.room.repository.RecordRepository
import com.elife.room.repository.RoomRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * 记录新增/编辑页面 ViewModel
 * @date 2026/4/4
 */
class RecordEditViewModel(
    private val roomRepository: RoomRepository,
    private val recordRepository: RecordRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecordEditUiState())
    val uiState: StateFlow<RecordEditUiState> = _uiState.asStateFlow()


    /**
     * 加载记录数据
     */
    fun loadRecord(roomId: Long, recordId: Long) = viewModelScope.launch {
        _uiState.value = RecordEditUiState(isLoading = true)
        if (roomId == Long.defaultId) {
            _uiState.update { it.copy(isLoading = false, recordInfoList = emptyList()) }
            return@launch
        }
        val recordInfoListDeferred = async {
            if (recordId == Long.defaultId) {
                // 新增记录
                recordRepository.getBlankRecordInfoList(roomId)
            } else {
                // 编辑记录
                recordRepository.getRecordListById(roomId, recordId)
            }
        }
        val plugInfoListDeferred = async {
            if (recordId == Long.defaultId) {
                // 查询支持的搜索插件
                emptyList()
            } else {
                emptyList<Long>()
            }
        }
        _uiState.update {
            it.copy(
                isLoading = false,
                recordInfoList = recordInfoListDeferred.await(),
                searchPluginList = plugInfoListDeferred.await()
            )
        }
    }

    /**
     * 更新字段值
     */
    fun updateFieldValue(attributeId: Long, value: String) {
        val currentList = _uiState.value.recordInfoList.toMutableList()
        val currentRecordInfo = currentList.firstOrNull { it.attribute.id == attributeId } ?: return
        val newRecordInfo = currentRecordInfo.copy(value = value)
        val index = currentList.indexOf(currentRecordInfo)
        currentList.removeAt(index)
        currentList.add(index, newRecordInfo)
        _uiState.update { it.copy(recordInfoList = currentList) }
    }

    fun saveRecord() = viewModelScope.launch {
        recordRepository.saveRecordInfo(_uiState.value.recordInfoList.filter { it.value.isNotBlank() })
        _uiState.update { it.copy(saved = true) }
    }

    /**
     * 验证表单
     */
    fun validateForm(): Boolean {
        // val state = _uiState.value
        var isValid = true
        // val updatedFields = mutableMapOf<Long, FormFieldState>()

        // state.attributes.forEach { attr ->
        //     val fieldState = state.fieldValues[attr.id] ?: FormFieldState()
        //     val error = when {
        //         attr.required && fieldState.value.isBlank() -> "${attr.name} 不能为空"
        //         else -> null
        //     }
        //     if (error != null) isValid = false
        //     updatedFields[attr.id] = fieldState.copy(error = error, isValid = error == null)
        // }

        return isValid
    }
}

data class RecordEditUiState(
    val isLoading: Boolean = false,
    val msg: String = "",
    val saved: Boolean = false,
    val recordInfoList: List<RecordInfo> = emptyList(),
    val searchPluginList: List<Long> = emptyList()
)