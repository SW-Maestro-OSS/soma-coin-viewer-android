package com.soma.coinviewer.domain.model.exchangerate

import java.math.BigDecimal

data class ExchangeRate(
    val currencyCode: String,
    val receiveRateInWon: BigDecimal,
)
