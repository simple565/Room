package com.elife.room.core.ext

/**
 * @author Lian
 * @date 2025/12/9
 */

val Int.Companion.defaultId: Int
    get() = 0

val Int.isDefaultId : Boolean
    get() = this == Int.defaultId