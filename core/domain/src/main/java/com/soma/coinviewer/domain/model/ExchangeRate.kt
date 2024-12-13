package com.soma.coinviewer.domain.model

import com.soma.coinviewer.domain.preferences.CurrencyCode
import java.math.BigDecimal

data class ExchangeRate(
    val currencyCode: CurrencyCode,
    val receiveRateInWon: BigDecimal,
    val sendRateToForeignCurrency: BigDecimal,
) {
    override fun toString(): String {
        return "currencyCode=${currencyCode.value}," +
                "receiveRateInWon=$receiveRateInWon," +
                "sendRateToForeignCurrency=$sendRateToForeignCurrency"
    }

    companion object {
        fun fromString(data: String): ExchangeRate {
            val properties = data.split(",").associate {
                val (key, value) = it.split("=")
                key to value
            }

            return ExchangeRate(
                currencyCode = CurrencyCode.fromValue(
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
        }
    }
}