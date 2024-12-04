package com.soma.coinviewer.data.network.datasource

import com.soma.coinviewer.data.network.api.OpenApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ExchangeRateDataSource @Inject constructor(
    private val openApi: OpenApi,
) {
    suspend fun getExchangeRate(searchDate: LocalDate) = openApi.getExchangeRate(
        searchDate = searchDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
    )
}