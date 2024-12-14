package com.soma.coinviewer.data.datastore.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.soma.coinviewer.data.datastore.util.getPreference
import com.soma.coinviewer.data.datastore.util.savePreference
import com.soma.coinviewer.domain.model.ExchangeRate
import com.soma.coinviewer.domain.preferences.PriceCurrencyUnit
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal
import javax.inject.Inject

class LocalExchangeRateDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun setExchangeRate(exchangeRate: ExchangeRate) {
        dataStore.savePreference(
            stringPreferencesKey(EXCHANGE_RATE_PREFERENCE_KEY + exchangeRate.priceCurrencyUnit.currencyCode),
            exchangeRate,
        ) {
            "currencyCode=${it.priceCurrencyUnit.currencyCode}," +
                    "receiveRateInWon=${it.receiveRateInWon}," +
                    "sendRateToForeignCurrency=${it.sendRateToForeignCurrency}"
        }
    }

    fun getExchangeRate(currencyUnit: PriceCurrencyUnit): Flow<ExchangeRate> {
        return dataStore.getPreference(
            stringPreferencesKey(EXCHANGE_RATE_PREFERENCE_KEY + currencyUnit.currencyCode)
        ) { data ->
            data?.let {
                val properties = it.split(",").associate {
                    val (key, value) = it.split("=")
                    key to value
                }

                ExchangeRate(
                    priceCurrencyUnit = PriceCurrencyUnit.fromValue(
                        properties["currencyCode"]
                            ?: throw IllegalArgumentException("currencyCode가 유효하지 않습니다.")
                    ),
                    receiveRateInWon = BigDecimal(
                        properties["receiveRateInWon"]
                            ?: throw IllegalArgumentException("receiveRateInWon가 유효하지 않습니다.")
                    ),
                    sendRateToForeignCurrency = BigDecimal(
                        properties["sendRateToForeignCurrency"]
                            ?: throw IllegalArgumentException("sendRateToForeignCurrency가 유효하지 않습니다.")
                    ),
                )
            } ?: throw IllegalArgumentException("환율 데이터를 찾을 수 없습니다.")
        }
    }

    companion object {
        private const val EXCHANGE_RATE_PREFERENCE_KEY = "exchange_rate_"
    }
}