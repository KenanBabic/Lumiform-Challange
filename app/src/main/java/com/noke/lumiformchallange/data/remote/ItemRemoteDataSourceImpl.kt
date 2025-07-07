package com.noke.lumiformchallange.data.remote

import com.noke.lumiformchallange.data.Result
import com.noke.lumiformchallange.data.model.ItemApiModel
import javax.inject.Inject

class ItemRemoteDataSourceImpl @Inject constructor(
    private val itemApiService: ItemApiService,
    private val networkErrorHandler: NetworkErrorHandler
) : ItemRemoteDataSource {

    override suspend fun getItems(): Result<ItemApiModel> {
        return try {
            val result = itemApiService.getItems()
            Result.Success(result)

        } catch (e: Exception) {
            Result.Error(e)
            networkErrorHandler.handleError<ItemApiModel>(e)
        }
    }
}