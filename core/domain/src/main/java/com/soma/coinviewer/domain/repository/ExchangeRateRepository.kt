package com.soma.coinviewer.domain.repository

import com.soma.coinviewer.domain.model.exchangerate.ExchangeRate

interface ExchangeRateRepository {
    suspend fun updateExchangeRate()
    suspend fun getExchangeRate(currencyCode: String): ExchangeRate
}
