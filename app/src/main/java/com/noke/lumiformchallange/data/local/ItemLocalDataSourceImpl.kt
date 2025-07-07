package com.noke.lumiformchallange.data.local

import com.google.gson.GsonBuilder
import com.noke.lumiformchallange.data.local.dao.ItemDao
import com.noke.lumiformchallange.data.local.entity.ItemEntity
import com.noke.lumiformchallange.data.model.ApiModelDeserializer
import com.noke.lumiformchallange.data.model.ItemApiModel
import com.noke.lumiformchallange.data.toApiModel
import com.noke.lumiformchallange.data.toDomain
import com.noke.lumiformchallange.domain.model.Item
import javax.inject.Inject

class ItemLocalDataSourceImpl @Inject constructor(
    private val itemDao: ItemDao
) : ItemLocalDataSource {

    companion object {
        private const val CACHE_VALIDITY_DURATION = 24 * 60 * 60 * 1000L
    }

    private val gson = GsonBuilder()
        .registerTypeAdapter(ItemApiModel::class.java, ApiModelDeserializer())
        .create()

    override suspend fun saveItems(item: Item) {
        try {
            val apiModel = item.toApiModel()
            val entity = ItemEntity(
                jsonData = gson.toJson(apiModel),
                updatedAt = System.currentTimeMillis()
            )
            itemDao.insertContent(entity)
        } catch (e: Exception) {
            // Log error
        }
    }

    override suspend fun getItems(): Item? {
        return try {
            val entity = itemDao.getContent()
            val apiModel = gson.fromJson(entity?.jsonData ?: "", ItemApiModel::class.java)
            val item = apiModel.toDomain()

            return item
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun isCacheValid(): Boolean {
        return try {
            val updatedAt = itemDao.getUpdatedAt() ?: return false
            (System.currentTimeMillis() - updatedAt) < CACHE_VALIDITY_DURATION
        } catch (e: Exception) {
            false
        }
    }
}