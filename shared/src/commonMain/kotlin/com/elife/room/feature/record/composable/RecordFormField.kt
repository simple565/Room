package com.elife.room.feature.record.composable

import androidx.compose.ui.Modifier
import com.elife.room.entity.RoomAttributeInfo

/**
 * 记录表单字段组件基类
 * @author Lian
 * @date 2026/4/4
 */
interface RecordFormField {
    val attribute: RoomAttributeInfo
    val value: String
    val onValueChange: (String) -> Unit
    val modifier: Modifier
    val enabled: Boolean
}

/**
 * 表单字段状态
 */
data class FormFieldState(
    val value: String = "",
    val error: String? = null,
    val isValid: Boolean = true
)

/**
 * 表单字段验证器
 */
fun interface FieldValidator {
    fun validate(value: String): String?
}

/**
 * 必填验证器
 */
class RequiredValidator(private val fieldName: String) : FieldValidator {
    override fun validate(value: String): String? {
        return if (value.isBlank()) "$fieldName 不能为空" else null
    }
}

/**
 * 数字范围验证器
 */
class NumberRangeValidator(
    private val fieldName: String,
    private val min: Float,
    private val max: Float
) : FieldValidator {
    override fun validate(value: String): String? {
        val number = value.toFloatOrNull()
        return when {
            number == null -> "$fieldName 必须是有效数字"
            number < min -> "$fieldName 不能小于 $min"
            number > max -> "$fieldName 不能大于 $max"
            else -> null
        }
    }
}

