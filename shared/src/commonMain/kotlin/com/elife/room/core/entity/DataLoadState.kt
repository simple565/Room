package com.elife.room.core.entity

/**
 * 数据加载状态
 * @date 2025/3/9
 */
data class DataLoadState<T>(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val msg: String = "",
    val dataList: List<T> = emptyList()
)