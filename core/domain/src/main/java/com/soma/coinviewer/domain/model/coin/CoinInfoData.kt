package com.soma.coinviewer.domain.model.coin

import java.math.BigDecimal

data class CoinInfoData(
    val symbol: String,
    val totalTradedQuoteAssetVolume: BigDecimal,
    val price: BigDecimal,
    val priceChangePercent: BigDecimal,
    val coinIconUrl: String,
)
