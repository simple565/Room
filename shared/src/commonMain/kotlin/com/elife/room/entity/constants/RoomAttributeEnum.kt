package com.elife.room.entity.constants

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
    LABEl_GROUP("LabelGroup"),
    NUMBER("Number"),
    SELECT("Select"),
    MULTI_SELECT("MultiSelect"),
    RATING("Rating"),
    SWITCH("Switch"),
    TEXT("Text"),
    LONG_TEXT("LongText");

    companion object {
        fun typeOf(type: String): RoomAttributeType {
            return entries.firstOrNull { it.type == type } ?: throw IllegalArgumentException("Invalid RoomAttributeType: $type")
        }
    }

}

enum class NumberFormat(val format: String) {
    NUMBER("Number"),
    /**
     * 数字格式，带逗号分隔
     */
    NUMBER_WITH_COMMAS("Number with commas"),
    PERCENT("Percent");
}

enum class ProgressBarStyle(val style: String) {
    NONE("None"),
    HORIZONTAL("Horizontal"),
    CIRCLE("Circle"),
}

enum class DatetimeFormat(val displayName: String, val format: String) {
    FULL("完整", "yyyy-MM-dd HH:mm:ss"),
    DATE("日期", "yyyy-MM-dd"),
    TIME("时间", "HH:mm:ss");
}

enum class DurationUnit(val displayName: String) {
    SECOND("秒"),
    MINUTE("分"),
    HOUR("时"),
    DAY("日"),
    MONTH("月"),
    YEAR("年");
}