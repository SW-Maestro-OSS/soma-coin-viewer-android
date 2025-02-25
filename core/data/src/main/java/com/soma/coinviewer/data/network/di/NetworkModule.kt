package com.soma.coinviewer.data.network.di

import com.soma.coinviewer.data.BuildConfig
import com.soma.coinviewer.data.network.api.ExchangeRateApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BINANCE_STREAM_BASE_URL = "wss://fstream.binance.com/ws/!ticker@arr"
    private const val OPEN_API_BASE_URL = "https://www.koreaexim.go.kr/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideWebSocketRequest(): Request {
        return Request.Builder()
            .url(BINANCE_STREAM_BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideOpenApi(okHttpClient: OkHttpClient): ExchangeRateApi = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(OPEN_API_BASE_URL)
        .build()
        .create(ExchangeRateApi::class.java)
}
