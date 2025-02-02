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
            stringPreferencesKey(EXCHANGE_RATE_PREFERENCE_KEY + exchangeRate.currencyCode),
            exchangeRate,
        ) { it.toString() }
    }

    fun getExchangeRate(currencyCode: String): Flow<ExchangeRate> {
        return dataStore.getPreference(
            stringPreferencesKey(EXCHANGE_RATE_PREFERENCE_KEY + currencyCode)
        ) { data ->
            data?.let {
                val properties = it
                    .removePrefix("ExchangeRate(").removeSuffix(")")
                    .split(",")
                    .associate {
                        val (key, value) = it.split("=")
                        key to value
                    }

                ExchangeRate(
                    currencyCode = properties["currencyCode"]
                        ?: throw NullPointerException("currencyCode가 유효하지 않습니다."),
                    receiveRateInWon = BigDecimal(
                        properties["receiveRateInWon"]
                            ?: throw NullPointerException("receiveRateInWon가 유효하지 않습니다.")
                    ),
                    sendRateToForeignCurrency = BigDecimal(
                        properties["sendRateToForeignCurrency"]
                            ?: throw NullPointerException("sendRateToForeignCurrency가 유효하지 않습니다.")
                    ),
                )
            } ?: throw NullPointerException("환율 데이터를 찾을 수 없습니다.")
        }
    }

    companion object {
        private const val EXCHANGE_RATE_PREFERENCE_KEY = "exchange_rate"
    }
}
