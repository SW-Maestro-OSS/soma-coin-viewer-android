package com.soma.coinviewer.domain.model

import java.math.BigDecimal

data class BinanceTickerData(
    val symbol: String,
    val totalTradedQuoteAssetVolume: BigDecimal,
    val price: BigDecimal,
    val priceChangePercent: BigDecimal,
    val coinIconUrl: String,
) {
    companion object {
        const val BINANCE_TICKER_DATA_MAX_SIZE = 30
    }
}
