package com.soma.coinviewer.data.network.model

import com.google.gson.annotations.SerializedName
import com.soma.coinviewer.domain.model.coin.CoinInfoData
import java.math.BigDecimal

data class BinanceTickerResponse(
    @SerializedName("e") val eventType: String?,
    @SerializedName("E") val eventTime: Long?,
    @SerializedName("s") val symbol: String?,
    @SerializedName("p") val priceChange: String?,
    @SerializedName("P") val priceChangePercent: String?,
    @SerializedName("w") val weightedAvgPrice: String?,
    @SerializedName("c") val lastPrice: String?,
    @SerializedName("Q") val lastQuantity: String?,
    @SerializedName("o") val openPrice: String?,
    @SerializedName("h") val highPrice: String?,
    @SerializedName("l") val lowPrice: String?,
    @SerializedName("v") val totalTradedBaseAssetVolume: String?,
    @SerializedName("q") val totalTradedQuoteAssetVolume: String?,
    @SerializedName("O") val statisticsOpenTime: Long?,
    @SerializedName("C") val statisticsCloseTime: Long?,
    @SerializedName("F") val firstTradeId: Long?,
    @SerializedName("L") val lastTradeId: Long?,
    @SerializedName("n") val totalTrades: Int?,
) {
    fun toVO() = CoinInfoData(
        symbol = symbol ?: "UNKNOWN",
        totalTradedQuoteAssetVolume = totalTradedQuoteAssetVolume?.toBigDecimalOrNull()
            ?: BigDecimal.ZERO,
        price = lastPrice?.toBigDecimalOrNull() ?: BigDecimal.ZERO,
        priceChangePercent = priceChangePercent?.toBigDecimalOrNull() ?: BigDecimal.ZERO,
        coinIconUrl =
        "https://raw.githubusercontent.com/spothq/cryptocurrency-icons/master/32/icon/" +
                "${symbol?.removeSuffix("USDT")?.lowercase()}.png"
    )
}
