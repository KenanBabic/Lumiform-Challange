package com.noke.lumiformchallange.data.remote

import com.noke.lumiformchallange.data.model.ApiModel
import retrofit2.http.GET

interface ItemApiService {
    @GET("v1/f118b9f0-6f84-435e-85d5-faf4453eb72a")
    suspend fun getContent(): ApiModel
}