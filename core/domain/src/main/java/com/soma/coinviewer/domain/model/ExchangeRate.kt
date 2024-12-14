package com.soma.coinviewer.domain.model

import com.soma.coinviewer.domain.preferences.PriceCurrencyUnit
import java.math.BigDecimal

data class ExchangeRate(
    val priceCurrencyUnit: PriceCurrencyUnit,
    val receiveRateInWon: BigDecimal,
    val sendRateToForeignCurrency: BigDecimal,
)
