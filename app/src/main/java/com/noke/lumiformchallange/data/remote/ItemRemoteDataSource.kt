package com.noke.lumiformchallange.data.remote

import com.noke.lumiformchallange.data.Result
import com.noke.lumiformchallange.data.toDomain
import com.noke.lumiformchallange.domain.model.Item
import javax.inject.Inject

class ItemRemoteDataSource @Inject constructor(
    private val itemApiService: ItemApiService,
) {

    suspend fun getContent(): Result<Item> {
        return try {
            val apiModel = itemApiService.getContent()
            val domainItem = apiModel.toDomain()
            Result.Success(domainItem)

        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}