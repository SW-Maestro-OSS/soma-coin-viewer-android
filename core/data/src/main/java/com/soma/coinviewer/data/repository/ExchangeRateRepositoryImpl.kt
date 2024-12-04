package com.soma.coinviewer.data.repository

import android.util.Log
import com.soma.coinviewer.data.datasource.ExchangeRateDataSource
import com.soma.coinviewer.domain.repository.ExchangeRateRepository
import javax.inject.Inject

class ExchangeRateRepositoryImpl @Inject constructor(
    private val exchangeRateDataSource: ExchangeRateDataSource,
) : ExchangeRateRepository {
    override suspend fun getExchangeRate() {
        val result = exchangeRateDataSource.getExchangeRate()
        Log.d("test", result.body().toString())
    }
}