package com.elife.room.database.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_plugin")
data class SearchPlugin(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val description: String,
    val url: String,
    val heads: String,
    val params: String,
    val body: String,
    val mapping: String
)
