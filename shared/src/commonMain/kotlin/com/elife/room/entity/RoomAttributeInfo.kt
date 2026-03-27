package com.elife.room.entity

/**
 * 房间属性
 * @author lianml
 * @date 2025/11/27
 */
interface RoomAttributeInfo {
    val type: RoomAttributeType
    val id: Int
    val name: String
    val description: String
    val required: Boolean
    val defaultValue: String
    val sortOrder: Int
}

data class TextRoomAttributeInfo(
    override val type: RoomAttributeType = RoomAttributeType.TEXT,
    override val id: Int,
    override val name: String,
    override val description: String,
    override val required: Boolean,
    override val defaultValue: String,
    override val sortOrder: Int
) : RoomAttributeInfo

data class ImageRoomAttributeInfo(
    override val type: RoomAttributeType = RoomAttributeType.IMAGE,
    override val id: Int,
    override val name: String,
    override val description: String,
    override val required: Boolean,
    override val defaultValue: String,
    override val sortOrder: Int
) : RoomAttributeInfo

data class NumberRoomAttributeInfo(
    override val type: RoomAttributeType = RoomAttributeType.NUMBER,
    override val id: Int,
    override val name: String,
    override val description: String,
    override val required: Boolean,
    override val defaultValue: String,
    override val sortOrder: Int,
    val unit: String = "",
    val minValue: Float = 0F,
    val maxValue: Float = 100F,
    val format: NumberFormat = NumberFormat.NUMBER,
    val allowDecimal: Boolean = false,
    val decimalPlaces: Int = 0,
    val progressBarStyle: ProgressBarStyle = ProgressBarStyle.NONE
) : RoomAttributeInfo

data class SwitchRoomAttributeInfo(
    override val type: RoomAttributeType = RoomAttributeType.SWITCH,
    override val id: Int,
    override val name: String,
    override val description: String,
    override val required: Boolean,
    override val defaultValue: String,
    override val sortOrder: Int
) : RoomAttributeInfo

data class SelectRoomAttributeInfo(
    override val type: RoomAttributeType = RoomAttributeType.SELECT,
    override val id: Int,
    override val name: String,
    override val description: String,
    override val required: Boolean,
    override val defaultValue: String,
    override val sortOrder: Int,
    val options: List<String> = emptyList()
) : RoomAttributeInfo

data class MultiSelectRoomAttributeInfo(
    override val type: RoomAttributeType = RoomAttributeType.MULTI_SELECT,
    override val id: Int,
    override val name: String,
    override val description: String,
    override val required: Boolean,
    override val defaultValue: String,
    override val sortOrder: Int,
    val options: List<String> = emptyList()
) : RoomAttributeInfo

data class DatetimeRoomAttributeInfo(
    override val type: RoomAttributeType = RoomAttributeType.DATETIME,
    override val id: Int,
    override val name: String,
    override val description: String,
    override val required: Boolean,
    override val defaultValue: String,
    override val sortOrder: Int,
    val datetime: String,
    val dateFormate: String = DatetimeFormat.FULL.format,
    val use24HourFormate: Boolean = false
) : RoomAttributeInfo

data class DurationRoomAttributeInfo(
    override val type: RoomAttributeType = RoomAttributeType.DURATION,
    override val id: Int,
    override val name: String,
    override val description: String,
    override val required: Boolean,
    override val defaultValue: String,
    override val sortOrder: Int,
    val minValue: Int = 0,
    val supportedUnits: List<DurationUnit> = listOf(DurationUnit.HOUR)
) : RoomAttributeInfo