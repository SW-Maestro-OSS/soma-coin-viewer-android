package com.soma.coinviewer.data.datasource

import com.soma.coinviewer.data.api.OpenApi
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ExchangeRateDataSource @Inject constructor(
    private val openApi: OpenApi,
) {
    suspend fun getExchangeRate() = openApi.getExchangeRate(
        searchDate = LocalDate.now(ZoneId.of("Asia/Seoul"))
            .format(DateTimeFormatter.ofPattern("yyyyMMdd"))
    )
}