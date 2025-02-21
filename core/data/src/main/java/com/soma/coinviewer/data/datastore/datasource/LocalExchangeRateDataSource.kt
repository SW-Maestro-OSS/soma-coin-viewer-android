package com.soma.coinviewer.data.datastore.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.soma.coinviewer.data.datastore.util.getPreference
import com.soma.coinviewer.data.datastore.util.savePreference
import com.soma.coinviewer.domain.model.exchangerate.ExchangeRate
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal
import javax.inject.Inject

class LocalExchangeRateDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun setExchangeRate(exchangeRate: ExchangeRate) {
        dataStore.savePreference(
            key = stringPreferencesKey(EXCHANGE_RATE_PREFERENCE_KEY + exchangeRate.currencyCode),
            value = exchangeRate.receiveRateInWon,
        ) { it.stripTrailingZeros().toPlainString() }
    }

    fun retrieveExchangeRate(currencyCode: String): Flow<BigDecimal> {
        return dataStore.getPreference(
            stringPreferencesKey(EXCHANGE_RATE_PREFERENCE_KEY + currencyCode)
        ) { data ->
            data?.let {
                try {
                    BigDecimal(it)
                } catch (e: Exception) {
                    throw IllegalStateException("환율 데이터 변환 실패: $it", e)
                }
            } ?: throw NullPointerException("$currencyCode 환율 데이터를 찾을 수 없습니다.")
        }
    }


    companion object {
        private const val EXCHANGE_RATE_PREFERENCE_KEY = "exchange_rate"
    }
}
