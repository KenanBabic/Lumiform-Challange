package com.noke.lumiformchallange.data.remote

import com.noke.lumiformchallange.data.Result
import com.noke.lumiformchallange.data.model.ItemApiModel

interface ItemRemoteDataSource {
    suspend fun getItems(): Result<ItemApiModel>
}

