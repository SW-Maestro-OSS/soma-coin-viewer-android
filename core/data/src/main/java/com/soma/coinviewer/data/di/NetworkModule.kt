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
    private const val BINANCE_STREAM_BASE_URL = "wss://stream.binance.com:9443/ws/@trade"

    // 로깅인터셉터 세팅
    @Provides
    @Singleton
    fun provideRequestHttpLoggingInterceptor() : HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    // OKHttpClient에 로깅인터셉터 등록
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