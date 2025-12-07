package com.elife.room.entity

/**
 * @author lianml
 * @date 2025/11/27
 */
/**
 * 属性类型
 */
enum class RoomAttributeType(val type: String) {
    DATETIME("Datetime"),
    DURATION("Duration"),
    IMAGE("Image"),
    NUMBER("Number"),
    SELECT("Select"),
    MULTI_SELECT("MultiSelect"),
    SWITCH("Switch"),
    TEXT("Text"),
}

enum class NumberFormat(val format: String) {
    NUMBER("Number"),
    NUMBER_WITH_COMMAS("Number with commas"),
    PERCENT("Percent"),
    STAR("Star"),
}

enum class ProgressBarStyle(val style: String) {
    NONE("None"),
    HORIZONTAL("Horizontal"),
    CIRCLE("Circle"),
}

enum class DatetimeFormat(val displayName: String, val format: String) {
    FULL("完整", "yyyy-MM-dd HH:mm:ss"),
    DATE_ONLY("仅日期", "yyyy-MM-dd"),
    TIME_ONLY("仅时间", "HH:mm:ss"),
}