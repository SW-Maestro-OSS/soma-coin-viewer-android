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

data class BinanceTickerKey(
    val volume: BigDecimal,
    val symbol: String
) : Comparable<BinanceTickerKey> {
    override fun compareTo(other: BinanceTickerKey): Int {
        return compareBy<BinanceTickerKey> { it.volume }
            .thenBy { it.symbol }
            .compare(this, other)
    }
}