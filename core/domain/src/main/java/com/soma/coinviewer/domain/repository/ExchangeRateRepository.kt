package com.soma.coinviewer.domain.repository

import com.soma.coinviewer.domain.model.ExchangeRate
import com.soma.coinviewer.domain.preferences.CurrencyCode

interface ExchangeRateRepository {
    suspend fun updateExchangeRate()
    suspend fun getExchangeRate(currencyCode: CurrencyCode): ExchangeRate
}