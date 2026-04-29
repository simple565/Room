package com.elife.room.core

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

actual fun initLogger() {
    Napier.base(DebugAntilog())
}
