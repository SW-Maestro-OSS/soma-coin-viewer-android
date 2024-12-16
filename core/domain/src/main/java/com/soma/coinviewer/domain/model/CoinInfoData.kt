package com.soma.coinviewer.domain.model

import java.math.BigDecimal

data class CoinInfoData(
    val symbol: String,
    val totalTradedQuoteAssetVolume: BigDecimal,
    val price: BigDecimal,
    val priceChangePercent: BigDecimal,
    val coinIconUrl: String,
) {
    companion object {
        const val COIN_INFO_TICKER_DATA_MAX_SIZE = 30
    }
}

data class CoinInfoKey(
    val volume: BigDecimal,
    val symbol: String
) : Comparable<CoinInfoKey> {
    override fun compareTo(other: CoinInfoKey): Int {
        return compareBy<CoinInfoKey> { it.volume }
            .thenBy { it.symbol }
            .compare(this, other)
    }
}