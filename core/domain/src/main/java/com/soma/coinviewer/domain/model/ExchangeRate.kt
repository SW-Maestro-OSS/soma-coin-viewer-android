package com.soma.coinviewer.domain.model

import java.math.BigDecimal

data class ExchangeRate(
    val currencyCode: String,
    val receiveRateInWon: BigDecimal,
    val sendRateToForeignCurrency: BigDecimal,
)
