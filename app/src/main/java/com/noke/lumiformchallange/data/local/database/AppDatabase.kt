package com.noke.lumiformchallange.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.noke.lumiformchallange.data.local.dao.ItemDao
import com.noke.lumiformchallange.data.local.entity.ItemEntity

@Database(
    entities = [ItemEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}