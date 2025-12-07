package com.elife.room.core.ext

import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.window.core.layout.WindowSizeClass

/**
 * @author lianml
 * @date 2025/11/6
 */
fun <T> WindowAdaptiveInfo.adaptiveValue(compact: T, medium: T, expanded: T): T {
    val windowSizeClass = this.windowSizeClass
    return when {
        windowSizeClass.isWidthAtLeastBreakpoint(840) -> expanded
        windowSizeClass.isWidthAtLeastBreakpoint(600) -> medium
        else -> compact
    }
}

fun WindowAdaptiveInfo.isCompact(): Boolean {
    return this.windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND).not()
            && this.windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND).not()
}