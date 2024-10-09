package com.soma.coinviewer.data.model

import com.google.gson.annotations.SerializedName
import com.soma.coinviewer.domain.entity.BinanceTickerData
import java.math.BigDecimal

data class BinanceTickerResponse(
    @SerializedName("e") val eventType: String? = null,
    @SerializedName("E") val eventTime: Long? = null,
    @SerializedName("s") val symbol: String? = null,
    @SerializedName("p") val priceChange: String? = null,
    @SerializedName("P") val priceChangePercent: String? = null,
    @SerializedName("w") val weightedAvgPrice: String? = null,
    @SerializedName("c") val lastPrice: String? = null,
    @SerializedName("Q") val lastQuantity: String? = null,
    @SerializedName("o") val openPrice: String? = null,
    @SerializedName("h") val highPrice: String? = null,
    @SerializedName("l") val lowPrice: String? = null,
    @SerializedName("v") val totalTradedBaseAssetVolume: String? = null,
    @SerializedName("q") val totalTradedQuoteAssetVolume: String? = null,
    @SerializedName("O") val statisticsOpenTime: Long? = null,
    @SerializedName("C") val statisticsCloseTime: Long? = null,
    @SerializedName("F") val firstTradeId: Long? = null,
    @SerializedName("L") val lastTradeId: Long? = null,
    @SerializedName("n") val totalTrades: Int? = null,
) {
    fun toVO() = BinanceTickerData(
        symbol = symbol ?: "",
        totalTradedQuoteAssetVolume = lastPrice?.toBigDecimalOrNull() ?: BigDecimal(0.0),
        price = lastPrice?.toBigDecimalOrNull() ?: BigDecimal(0.0),
        priceChangePercent = priceChangePercent?.toBigDecimalOrNull() ?: BigDecimal(0.0),
    )
}