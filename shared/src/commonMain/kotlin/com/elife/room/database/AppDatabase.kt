package com.elife.room.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.elife.room.database.dao.RecordDao
import com.elife.room.database.dao.RoomAttributeDao
import com.elife.room.database.dao.RoomDao
import com.elife.room.database.dao.RoomGroupDao
import com.elife.room.database.table.Room
import com.elife.room.database.table.RoomAttribute
import com.elife.room.database.table.RoomGroup
import com.elife.room.database.table.RoomRecord

@ConstructedBy(AppDatabaseConstructor::class)
@Database(
    entities = [Room::class, RoomAttribute::class, RoomGroup::class, RoomRecord::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DB_FILE_NAME = "Room.db"
    }

    abstract fun roomDao(): RoomDao

    abstract fun roomGroupDao(): RoomGroupDao

    abstract fun roomAttributeDao(): RoomAttributeDao

    abstract fun recordDao(): RecordDao
}

@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}
