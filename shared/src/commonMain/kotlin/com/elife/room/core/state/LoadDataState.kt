package com.elife.room.core.state

/**
 * @author Lian
 * @date 2026/4/23
 */
data class LoadDataState(
    val isLoading: Boolean = false,
    val error: String = "",
    val isEmpty: Boolean = false
)
