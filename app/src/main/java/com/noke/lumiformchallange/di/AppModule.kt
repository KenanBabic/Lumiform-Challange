package com.noke.lumiformchallange.di

import com.noke.lumiformchallange.data.remote.ItemApiService
import com.noke.lumiformchallange.data.remote.ItemRemoteDataSource
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
    fun provideItemRepository(
        itemRemoteDataSource: ItemRemoteDataSource,
    ): ItemRepository {
        return ItemRepositoryImpl(itemRemoteDataSource)
    }


}