package com.elife.room.entity

/**
 * 房间属性
 * @author lianml
 * @date 2025/11/27
 */
interface RoomAttribute {
    val type: RoomAttributeType
    val id: Int
    val name: String
    val description: String
    val required: Boolean
    val defaultValue: String
}

data class TextRoomAttribute(
    override val type: RoomAttributeType = RoomAttributeType.TEXT,
    override val id: Int,
    override val name: String,
    override val description: String,
    override val required: Boolean,
    override val defaultValue: String
) : RoomAttribute

data class ImageRoomAttribute(
    override val type: RoomAttributeType = RoomAttributeType.IMAGE,
    override val id: Int,
    override val name: String,
    override val description: String,
    override val required: Boolean,
    override val defaultValue: String
) : RoomAttribute

data class NumberRoomAttribute(
    override val type: RoomAttributeType = RoomAttributeType.NUMBER,
    override val id: Int,
    override val name: String,
    override val description: String,
    override val required: Boolean,
    override val defaultValue: String,
    val unit: String = "",
    val minValue: Float = 0F,
    val maxValue: Float = 100F,
    val format: NumberFormat = NumberFormat.NUMBER,
    val decimalPlaces: Int = 0,
    val progressBarStyle: ProgressBarStyle = ProgressBarStyle.NONE
) : RoomAttribute

data class SwitchRoomAttribute(
    override val type: RoomAttributeType = RoomAttributeType.SWITCH,
    override val id: Int,
    override val name: String,
    override val description: String,
    override val required: Boolean,
    override val defaultValue: String
) : RoomAttribute

data class SelectRoomAttribute(
    override val type: RoomAttributeType = RoomAttributeType.SELECT,
    override val id: Int,
    override val name: String,
    override val description: String,
    override val required: Boolean,
    override val defaultValue: String,
    val options: List<String> = emptyList()
) : RoomAttribute

data class MultiSelectRoomAttribute(
    override val type: RoomAttributeType = RoomAttributeType.MULTI_SELECT,
    override val id: Int,
    override val name: String,
    override val description: String,
    override val required: Boolean,
    override val defaultValue: String,
    val options: List<String> = emptyList()
) : RoomAttribute

data class DatetimeRoomAttribute(
    override val type: RoomAttributeType = RoomAttributeType.DATETIME,
    override val id: Int,
    override val name: String,
    override val description: String,
    override val required: Boolean,
    override val defaultValue: String,
    val datetime: String,
    val dateFormate: String = DatetimeFormat.FULL.format,
    val use24HourFormate: Boolean = false
) : RoomAttribute