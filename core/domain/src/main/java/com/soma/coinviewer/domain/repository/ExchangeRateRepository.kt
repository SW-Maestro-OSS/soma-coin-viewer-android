package com.soma.coinviewer.domain.repository

interface ExchangeRateRepository {
    suspend fun getExchangeRate()
}