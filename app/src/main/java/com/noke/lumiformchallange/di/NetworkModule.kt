package com.noke.lumiformchallange.di

import android.content.Context
import com.noke.lumiformchallange.data.remote.NetworkConnectivityChecker
import com.noke.lumiformchallange.data.remote.NetworkErrorHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkErrorHandler(): NetworkErrorHandler {
        return NetworkErrorHandler()
    }

    @Provides
    @Singleton
    fun provideNetworkConnectivityChecker(
        @ApplicationContext context: Context
    ): NetworkConnectivityChecker {
        return NetworkConnectivityChecker(context)
    }

}