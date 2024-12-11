package com.soma.coinviewer.data.network.api

import com.soma.coinviewer.data.BuildConfig
import com.soma.coinviewer.data.network.model.ExchangeRateResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenApi {
    @GET("site/program/financial/exchangeJSON")
    suspend fun getExchangeRate(
        @Query("authkey") authKey: String = BuildConfig.OPEN_API_KEY,
        @Query("searchdate") searchDate: String,
        @Query("data") data: String = "AP01",
    ): Response<List<ExchangeRateResponse>>
}