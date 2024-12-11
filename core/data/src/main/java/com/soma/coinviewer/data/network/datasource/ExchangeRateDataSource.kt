package com.soma.coinviewer.data.network.datasource

import android.util.Log
import com.soma.coinviewer.data.network.api.OpenApi
import com.soma.coinviewer.data.network.model.ExchangeRateResponse
import com.soma.coinviewer.data.network.util.onResponse
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ExchangeRateDataSource @Inject constructor(
    private val openApi: OpenApi,
) {
    suspend fun getExchangeRate(searchDate: LocalDate): List<ExchangeRateResponse> {
        return openApi.getExchangeRate(
            searchDate = searchDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        ).onResponse()
    }
}