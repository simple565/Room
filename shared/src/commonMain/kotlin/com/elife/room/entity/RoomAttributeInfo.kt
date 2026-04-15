package com.elife.room.entity

import com.elife.room.core.ext.defaultId
import kotlinx.serialization.Serializable

/**
 * 房间 属性
 * @author lianml
 * @date 2025/11/27
 */
@Serializable(with = RoomAttributeInfoSerializer::class)
interface RoomAttributeInfo {
    val type: String
    val id: Long
    val name: String
    val description: String
    val required: Boolean
    val defaultValue: String
    val sortOrder: Int
}

@Serializable
data class TextRoomAttributeInfo(
    override val type: String = RoomAttributeType.TEXT.type,
    override val id: Long = Long.defaultId,
    override val name: String,
    override val description: String,
    override val required: Boolean,
    override val defaultValue: String,
    override val sortOrder: Int
) : RoomAttributeInfo

@Serializable
data class ImageRoomAttributeInfo(
    override val type: String = RoomAttributeType.IMAGE.type,
    override val id: Long = Long.defaultId,
    override val name: String,
    override val description: String,
    override val required: Boolean,
    override val defaultValue: String,
    override val sortOrder: Int
) : RoomAttributeInfo

@Serializable
data class NumberRoomAttributeInfo(
    override val type: String = RoomAttributeType.NUMBER.type,
    override val id: Long = Long.defaultId,
    override val name: String,
    override val description: String,
    override val required: Boolean,
    override val defaultValue: String,
    override val sortOrder: Int,
    val unit: String = "",
    val minValue: Float = 0F,
    val maxValue: Float = 100F,
    val format: String = NumberFormat.NUMBER.name,
    val allowDecimal: Boolean = false,
    val decimalPlaces: Int = 0,
    val progressBarStyle: String = ProgressBarStyle.NONE.name
) : RoomAttributeInfo

@Serializable
data class SwitchRoomAttributeInfo(
    override val type: String = RoomAttributeType.SWITCH.type,
    override val id: Long = Long.defaultId,
    override val name: String,
    override val description: String,
    override val required: Boolean,
    override val defaultValue: String,
    override val sortOrder: Int
) : RoomAttributeInfo

@Serializable
data class SelectRoomAttributeInfo(
    override val type: String = RoomAttributeType.SELECT.type,
    override val id: Long = Long.defaultId,
    override val name: String,
    override val description: String,
    override val required: Boolean,
    override val defaultValue: String,
    override val sortOrder: Int,
    val options: List<String> = emptyList()
) : RoomAttributeInfo

@Serializable
data class MultiSelectRoomAttributeInfo(
    override val type: String = RoomAttributeType.MULTI_SELECT.type,
    override val id: Long = Long.defaultId,
    override val name: String,
    override val description: String,
    override val required: Boolean,
    override val defaultValue: String,
    override val sortOrder: Int,
    val options: List<String> = emptyList()
) : RoomAttributeInfo

@Serializable
data class DatetimeRoomAttributeInfo(
    override val type: String = RoomAttributeType.DATETIME.type,
    override val id: Long = Long.defaultId,
    override val name: String,
    override val description: String,
    override val required: Boolean,
    override val defaultValue: String,
    override val sortOrder: Int,
    val format: String = DatetimeFormat.FULL.format
) : RoomAttributeInfo

@Serializable
data class DurationRoomAttributeInfo(
    override val type: String = RoomAttributeType.DURATION.type,
    override val id: Long = Long.defaultId,
    override val name: String,
    override val description: String,
    override val required: Boolean,
    override val defaultValue: String,
    override val sortOrder: Int,
    val minValue: Int = 0,
    val supportedUnits: List<String> = listOf(DurationUnit.HOUR.name)
) : RoomAttributeInfo

@Serializable
data class RatingRoomAttributeInfo(
    override val type: String = RoomAttributeType.RATING.type,
    override val id: Long = Long.defaultId,
    override val name: String,
    override val description: String,
    override val required: Boolean,
    override val defaultValue: String,
    override val sortOrder: Int,
    val maxValue: Int = 5
) : RoomAttributeInfo

@Serializable
data class LongTextRoomAttributeInfo(
    override val type: String = RoomAttributeType.LONG_TEXT.type,
    override val id: Long = Long.defaultId,
    override val name: String,
    override val description: String,
    override val required: Boolean,
    override val defaultValue: String,
    override val sortOrder: Int
) : RoomAttributeInfo