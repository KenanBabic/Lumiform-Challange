package com.noke.lumiformchallange.domain.repository

import com.noke.lumiformchallange.data.Result
import com.noke.lumiformchallange.domain.model.Item

interface ItemRepository {
    suspend fun getContent(): Result<Item>

}