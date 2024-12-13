package com.soma.coinviewer.domain.repository

import com.soma.coinviewer.domain.model.ExchangeRate
import com.soma.coinviewer.domain.preferences.PriceCurrencyUnit

interface ExchangeRateRepository {
    suspend fun updateExchangeRate()
    suspend fun getExchangeRate(priceCurrencyUnit: PriceCurrencyUnit): ExchangeRate
}