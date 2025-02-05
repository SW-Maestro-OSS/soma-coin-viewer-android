package com.soma.coinviewer.domain.repository

import java.math.BigDecimal

interface ExchangeRateRepository {
    suspend fun updateExchangeRate()
    suspend fun getExchangeRate(currencyCode: String): BigDecimal
}
