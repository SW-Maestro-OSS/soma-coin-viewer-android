package com.soma.coinviewer.data.di

import com.google.gson.Gson
import com.soma.coinviewer.data.BuildConfig
import com.soma.coinviewer.data.api.OpenApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.MediaType.Companion.toMediaType
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
    fun provideWebSocket(): Request {
        return Request.Builder()
            .url(BINANCE_STREAM_BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideOpenApi(okHttpClient: OkHttpClient): OpenApi = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl()
        .build()
        .create(OpenApi::class.java)

}