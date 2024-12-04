package com.soma.coinviewer.domain.model

import java.math.BigDecimal

data class ExchangeRate(
    val currencyCode: String,
    val currencyName: String,
    val remittanceReceiveRate: BigDecimal,
    val remittanceSendRate: BigDecimal,
)
