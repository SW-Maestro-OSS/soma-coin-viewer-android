package com.soma.coinviewer.data.datastore.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.soma.coinviewer.data.datastore.util.getPreference
import com.soma.coinviewer.data.datastore.util.savePreference
import com.soma.coinviewer.domain.model.ExchangeRate
import com.soma.coinviewer.domain.preferences.CurrencyCode
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalExchangeRateDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun saveExchangeRate(exchangeRate: ExchangeRate) {
        dataStore.savePreference(
            stringPreferencesKey(EXCHANGE_RATE_PREFERENCE_KEY + exchangeRate.currencyCode.value),
            exchangeRate,
        ) { it.toString() }
    }

    fun getExchangeRate(currencyUnit: CurrencyCode): Flow<ExchangeRate> {
        return dataStore.getPreference(
            stringPreferencesKey(EXCHANGE_RATE_PREFERENCE_KEY + currencyUnit.value)
        ) { value ->
            value?.let { ExchangeRate.fromString(it) }
                ?: throw IllegalArgumentException("환율 데이터를 찾을 수 없습니다.")
        }
    }

    companion object {
        private const val EXCHANGE_RATE_PREFERENCE_KEY = "exchange_rate_"
    }
}