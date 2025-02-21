package com.soma.coinviewer.domain.repository

import java.math.BigDecimal

interface ExchangeRateRepository {
    suspend fun updateExchangeRate(): Result<Unit>
    suspend fun getExchangeRate(currencyCode: String): Result<BigDecimal>
}
