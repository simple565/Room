package com.elife.room.entity

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@OptIn(ExperimentalSerializationApi::class)
class RoomAttributeInfoSerializer : KSerializer<RoomAttributeInfo> {

    override val descriptor: SerialDescriptor
        get() = TextRoomAttributeInfo.serializer().descriptor

    override fun deserialize(decoder: Decoder): RoomAttributeInfo {
        val jsonDecoder = decoder as? JsonDecoder ?: throw SerializationException("Expected JsonDecoder")
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject
        val json = jsonDecoder.json

        val typeString = jsonObject["type"]?.jsonPrimitive?.content ?: throw SerializationException("Missing type field")
        val type = RoomAttributeType.entries.find { it.type.equals(typeString, true) } ?: throw SerializationException("Unknown type: $typeString")

        return when (type) {
            RoomAttributeType.TEXT -> json.decodeFromJsonElement(TextRoomAttributeInfo.serializer(), element)
            RoomAttributeType.IMAGE -> json.decodeFromJsonElement(ImageRoomAttributeInfo.serializer(), element)
            RoomAttributeType.NUMBER -> json.decodeFromJsonElement(NumberRoomAttributeInfo.serializer(), element)
            RoomAttributeType.SWITCH -> json.decodeFromJsonElement(SwitchRoomAttributeInfo.serializer(), element)
            RoomAttributeType.SELECT -> json.decodeFromJsonElement(SelectRoomAttributeInfo.serializer(), element)
            RoomAttributeType.MULTI_SELECT -> json.decodeFromJsonElement(MultiSelectRoomAttributeInfo.serializer(), element)
            RoomAttributeType.DATETIME -> json.decodeFromJsonElement(DatetimeRoomAttributeInfo.serializer(), element)
            RoomAttributeType.DURATION -> json.decodeFromJsonElement(DurationRoomAttributeInfo.serializer(), element)
            RoomAttributeType.RATING -> json.decodeFromJsonElement(RatingRoomAttributeInfo.serializer(), element)
            RoomAttributeType.LONG_TEXT -> json.decodeFromJsonElement(LongTextRoomAttributeInfo.serializer(), element)
        }
    }

    override fun serialize(encoder: Encoder, value: RoomAttributeInfo) {
        when (value) {
            is TextRoomAttributeInfo -> TextRoomAttributeInfo.serializer().serialize(encoder, value)
            is ImageRoomAttributeInfo -> ImageRoomAttributeInfo.serializer().serialize(encoder, value)
            is NumberRoomAttributeInfo -> NumberRoomAttributeInfo.serializer().serialize(encoder, value)
            is SwitchRoomAttributeInfo -> SwitchRoomAttributeInfo.serializer().serialize(encoder, value)
            is SelectRoomAttributeInfo -> SelectRoomAttributeInfo.serializer().serialize(encoder, value)
            is MultiSelectRoomAttributeInfo -> MultiSelectRoomAttributeInfo.serializer().serialize(encoder, value)
            is DatetimeRoomAttributeInfo -> DatetimeRoomAttributeInfo.serializer().serialize(encoder, value)
            is DurationRoomAttributeInfo -> DurationRoomAttributeInfo.serializer().serialize(encoder, value)
            is RatingRoomAttributeInfo -> RatingRoomAttributeInfo.serializer().serialize(encoder, value)
            is LongTextRoomAttributeInfo -> LongTextRoomAttributeInfo.serializer().serialize(encoder, value)
        }
    }
}