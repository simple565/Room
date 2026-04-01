package com.elife.room.entity

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = RoomAttributeInfo::class)
class RoomAttributeInfoSerializer : KSerializer<RoomAttributeInfo> {

    override val descriptor: SerialDescriptor
        get() = TextRoomAttributeInfo.serializer().descriptor

    override fun deserialize(decoder: Decoder): RoomAttributeInfo {
        val jsonDecoder = decoder as? JsonDecoder ?: throw SerializationException("Expected JsonDecoder")
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject

        val typeString = jsonObject["type"]?.jsonPrimitive?.content ?: throw SerializationException("Missing type field")
        val type = RoomAttributeType.entries.find { it.type == typeString } ?: throw SerializationException("Unknown type: $typeString")
        
        return when (type) {
            RoomAttributeType.TEXT -> jsonDecoder.json.decodeFromJsonElement(TextRoomAttributeInfo.serializer(), element)
            RoomAttributeType.IMAGE -> jsonDecoder.json.decodeFromJsonElement(ImageRoomAttributeInfo.serializer(), element)
            RoomAttributeType.NUMBER -> jsonDecoder.json.decodeFromJsonElement(NumberRoomAttributeInfo.serializer(), element)
            RoomAttributeType.SWITCH -> jsonDecoder.json.decodeFromJsonElement(SwitchRoomAttributeInfo.serializer(), element)
            RoomAttributeType.SELECT -> jsonDecoder.json.decodeFromJsonElement(SelectRoomAttributeInfo.serializer(), element)
            RoomAttributeType.MULTI_SELECT -> jsonDecoder.json.decodeFromJsonElement(MultiSelectRoomAttributeInfo.serializer(), element)
            RoomAttributeType.DATETIME -> jsonDecoder.json.decodeFromJsonElement(DatetimeRoomAttributeInfo.serializer(), element)
            RoomAttributeType.DURATION -> jsonDecoder.json.decodeFromJsonElement(DurationRoomAttributeInfo.serializer(), element)
            RoomAttributeType.RATING -> jsonDecoder.json.decodeFromJsonElement(RatingRoomAttributeInfo.serializer(), element)
            RoomAttributeType.LONG_TEXT -> jsonDecoder.json.decodeFromJsonElement(LongTextRoomAttributeInfo.serializer(), element)
        }
    }
}