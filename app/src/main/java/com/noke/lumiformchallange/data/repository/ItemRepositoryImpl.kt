package com.noke.lumiformchallange.data.repository

import com.noke.lumiformchallange.data.Result
import com.noke.lumiformchallange.data.Result.Error
import com.noke.lumiformchallange.data.Result.Success
import com.noke.lumiformchallange.data.flatten
import com.noke.lumiformchallange.data.local.ItemLocalDataSource
import com.noke.lumiformchallange.data.model.ItemApiModel
import com.noke.lumiformchallange.data.remote.ItemRemoteDataSource
import com.noke.lumiformchallange.data.toDomain
import com.noke.lumiformchallange.domain.model.Item
import com.noke.lumiformchallange.domain.repository.ItemRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val itemRemoteDataSource: ItemRemoteDataSource,
    private val itemLocalDataSource: ItemLocalDataSource
) : ItemRepository {

    companion object {
        private const val MAX_RETRY_ATTEMPTS = 3
        private const val RETRY_DELAY_MS = 1000L
    }

    // The offline mechanism returns the cashed data if there is one on the call. This work only if the remote content is not changed frequently.
    // If the remote content is changed frequently, we should implement offline first application which I thought would be overkill here
    override suspend fun getItems(): Result<List<Item>> {
        return try {
            when (val remoteResult = fetchWithRetry()) {
                is Success -> {
                    val result = remoteResult.data.toDomain()
                    itemLocalDataSource.saveItems(result)
                    Success(result.flatten())
                }

                is Error -> {
                    val cachedData = itemLocalDataSource.getItems()
                    if (cachedData != null && itemLocalDataSource.isCacheValid()) {
                        Success(cachedData.flatten())
                    } else {
                        Error(remoteResult.exception)
                    }
                }
            }
        } catch (e: Exception) {
            val cachedData = itemLocalDataSource.getItems()
            if (cachedData != null) {
                Success(cachedData.flatten())
            } else {
                Error(e)
            }
        }
    }

    private suspend fun fetchWithRetry(): Result<ItemApiModel> {
        var lastException: Exception? = null

        repeat(MAX_RETRY_ATTEMPTS) { attempt ->
            try {
                when (val result = itemRemoteDataSource.getItems()) {
                    is Success -> return result
                    is Error -> {
                        lastException = result.exception

                        if (attempt < MAX_RETRY_ATTEMPTS - 1) {
                            val delayMs = RETRY_DELAY_MS * (attempt + 1)
                            try {
                                delay(delayMs)
                            } catch (e: Exception) {
                                lastException = e
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                lastException = e
                if (attempt < MAX_RETRY_ATTEMPTS - 1) {
                    val delayMs = RETRY_DELAY_MS * (attempt + 1)
                    try {
                        delay(delayMs)
                    } catch (delayException: Exception) {
                        lastException = delayException
                    }
                }
            }
        }

        return Error(lastException ?: Exception("Max retry attempts exceeded"))
    }

}