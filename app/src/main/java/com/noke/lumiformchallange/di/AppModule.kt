package com.noke.lumiformchallange.di

import com.noke.lumiformchallange.data.local.ItemLocalDataSource
import com.noke.lumiformchallange.data.local.ItemLocalDataSourceImpl
import com.noke.lumiformchallange.data.local.dao.ItemDao
import com.noke.lumiformchallange.data.remote.ItemApiService
import com.noke.lumiformchallange.data.remote.ItemRemoteDataSource
import com.noke.lumiformchallange.data.remote.ItemRemoteDataSourceImpl
import com.noke.lumiformchallange.data.remote.NetworkErrorHandler
import com.noke.lumiformchallange.data.remote.NetworkModule
import com.noke.lumiformchallange.data.repository.ItemRepositoryImpl
import com.noke.lumiformchallange.domain.repository.ItemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesApiService(): ItemApiService{
        return NetworkModule.itemApiService
    }

    @Provides
    @Singleton
    fun provideItemLocalDataSource(
        itemDao: ItemDao
    ): ItemLocalDataSource {
        return ItemLocalDataSourceImpl(itemDao)
    }

    @Provides
    @Singleton
    fun provideItemRemoteDataSource(
        itemApiService: ItemApiService,
        networkErrorHandler: NetworkErrorHandler
    ): ItemRemoteDataSource {
        return ItemRemoteDataSourceImpl(itemApiService, networkErrorHandler)
    }


    @Provides
    @Singleton
    fun provideItemRepository(
        itemRemoteDataSource: ItemRemoteDataSource,
        itemLocalDataSource: ItemLocalDataSource
    ): ItemRepository {
        return ItemRepositoryImpl(itemRemoteDataSource, itemLocalDataSource)
    }

    @Provides
    @Singleton
    fun provideNetworkErrorHandler(): NetworkErrorHandler {
        return NetworkErrorHandler()
    }

}