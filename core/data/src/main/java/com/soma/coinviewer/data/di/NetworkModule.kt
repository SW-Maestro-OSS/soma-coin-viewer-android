package com.soma.coinviewer.data.di

import com.soma.coinviewer.data.datasource.WebSocketDataSourceImpl
import com.soma.coinviewer.domain.datasource.WebSocketDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
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
//            .readTimeout(30, TimeUnit.SECONDS)
//            .writeTimeout(30, TimeUnit.SECONDS)
//            .pingInterval(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun preSignedUrlDataSource(client: OkHttpClient): WebSocketDataSource {
        return WebSocketDataSourceImpl(client)
    }
}