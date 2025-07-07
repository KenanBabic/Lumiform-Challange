package com.noke.lumiformchallange.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.noke.lumiformchallange.data.local.entity.ItemEntity

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContent(item: ItemEntity)

    @Query("SELECT * FROM items_cache WHERE id = 1")
    suspend fun getContent(): ItemEntity?

    @Query("SELECT COUNT(*) > 0 FROM items_cache WHERE id = 1")
    suspend fun hasContent(): Boolean

    @Query("SELECT updatedAt FROM items_cache WHERE id = 1")
    suspend fun getUpdatedAt(): Long?

    @Query("DELETE FROM items_cache WHERE id = 1")
    suspend fun clearContent()
}