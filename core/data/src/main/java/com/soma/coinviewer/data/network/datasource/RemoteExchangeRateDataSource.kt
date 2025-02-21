package com.soma.coinviewer.data.network.datasource

import com.soma.coinviewer.data.network.api.ExchangeRateApi
import com.soma.coinviewer.data.network.model.ExchangeRateResponse
import com.soma.coinviewer.data.network.util.onResponse
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class RemoteExchangeRateDataSource @Inject constructor(
    private val exchangeRateApi: ExchangeRateApi,
) {
    suspend fun getExchangeRate(searchDate: LocalDate): Result<List<ExchangeRateResponse>> =
        exchangeRateApi.getExchangeRate(
            searchDate = searchDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        ).onResponse()
}
