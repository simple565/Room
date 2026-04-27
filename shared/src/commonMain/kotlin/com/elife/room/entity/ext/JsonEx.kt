package com.elife.room.entity.ext

import kotlinx.serialization.json.Json

val globalJson
    get() = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        prettyPrint = true
    }