package com.soma.coinviewer.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BINANCE_API_BASE_URL = "wss://ws-api.binance.com:443/ws-api/v3"
    private const val BINANCE_STREAM_BASE_URL = "wss://fstream.binance.com/ws/!ticker@arr"

    @Provides
    @Singleton
    fun provideRequestHttpLoggingInterceptor() : HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideWebSocket() : Request {
        return Request.Builder()
            .url(BINANCE_STREAM_BASE_URL)
            .build()
    }
}