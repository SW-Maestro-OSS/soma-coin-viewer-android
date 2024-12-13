package com.soma.coinviewer.domain.model

import com.soma.coinviewer.domain.preferences.PriceCurrencyUnit
import java.math.BigDecimal

data class ExchangeRate(
    val currencyCode: PriceCurrencyUnit,
    val receiveRateInWon: BigDecimal,
    val sendRateToForeignCurrency: BigDecimal,
) {
    override fun toString(): String {
        return "currencyCode=${currencyCode.value},receiveRateInWon=$receiveRateInWon,sendRateToForeignCurrency=$sendRateToForeignCurrency"
    }

    companion object {
        fun fromString(data: String): ExchangeRate {
            val properties = data.split(",").associate {
                val (key, value) = it.split("=")
                key to value
            }

            return ExchangeRate(
                currencyCode = PriceCurrencyUnit.fromValue(
                    properties["currencyCode"]
                        ?: throw IllegalArgumentException("Invalid currencyCode")
                ),
                receiveRateInWon = BigDecimal(
                    properties["receiveRateInWon"]
                        ?: throw IllegalArgumentException("Invalid receiveRateInWon")
                ),
                sendRateToForeignCurrency = BigDecimal(
                    properties["sendRateToForeignCurrency"]
                        ?: throw IllegalArgumentException("Invalid sendRateToForeignCurrency")
                ),
            )
        }
    }
