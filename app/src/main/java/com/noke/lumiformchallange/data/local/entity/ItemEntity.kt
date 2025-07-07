package com.noke.lumiformchallange.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items_cache")
data class ItemEntity(
    @PrimaryKey
    val id: Int = 1,
    val jsonData: String,
    val updatedAt: Long
)