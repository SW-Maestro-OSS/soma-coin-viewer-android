package com.soma.coinviewer.domain.repository

import com.soma.coinviewer.domain.model.ExchangeRate

interface ExchangeRateRepository {
    suspend fun getExchangeRate(): List<ExchangeRate>
}