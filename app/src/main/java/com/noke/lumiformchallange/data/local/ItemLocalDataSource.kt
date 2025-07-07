package com.noke.lumiformchallange.data.local

import com.noke.lumiformchallange.domain.model.Item

interface ItemLocalDataSource {
    suspend fun saveItems(item: Item)
    suspend fun getItems(): Item?
    suspend fun isCacheValid(): Boolean
}