package com.elife.room.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elife.room.database.table.SearchPlugin
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchPluginDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(plugin: SearchPlugin)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(plugins: List<SearchPlugin>)

    @Query("DELETE FROM search_plugin WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM search_plugin")
    fun getPluginList(): Flow<List<SearchPlugin>>

    @Query("SELECT * FROM search_plugin WHERE id = :id")
    suspend fun getPlugin(id: Int): SearchPlugin?
}
