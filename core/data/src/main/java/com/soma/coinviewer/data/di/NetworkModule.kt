package com.soma.coinviewer.data.di

import com.soma.coinviewer.data.datasource.BinanceDataSourceImpl
import com.soma.coinviewer.domain.datasource.BinanceDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    const val BINANCE_BASE_URL = "wss://ws-api.binance.com:443/ws-api/v3"

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
            .url(BINANCE_BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun binanceDataSource(okHttpClient: OkHttpClient, request: Request): BinanceDataSource {
        return BinanceDataSourceImpl(okHttpClient, request)
    }
}