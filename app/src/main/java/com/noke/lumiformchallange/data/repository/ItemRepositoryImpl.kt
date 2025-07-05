package com.noke.lumiformchallange.data.repository

import com.noke.lumiformchallange.data.Result
import com.noke.lumiformchallange.data.Result.Error
import com.noke.lumiformchallange.data.Result.Success
import com.noke.lumiformchallange.data.remote.ItemRemoteDataSource
import com.noke.lumiformchallange.domain.model.Item
import com.noke.lumiformchallange.domain.repository.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val itemRemoteDataSource: ItemRemoteDataSource
) : ItemRepository {

    override suspend fun getContent(): Result<Item> {
        return withContext(Dispatchers.IO) {
            try {
                when (val remoteResult = itemRemoteDataSource.getContent()) {
                    is Success -> {
                        Success(remoteResult.data)
                    }

                    is Error -> {
                        Error(remoteResult.exception)

                    }
                }
            } catch (e: Exception) {
                Error(e)

            }

        }
    }
}
