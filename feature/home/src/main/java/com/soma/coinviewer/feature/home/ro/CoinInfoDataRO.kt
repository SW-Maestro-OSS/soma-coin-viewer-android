package com.soma.coinviewer.feature.home.ro

import androidx.compose.ui.graphics.Color
import com.soma.coinviewer.domain.model.coin.CoinInfoData
import com.soma.coinviewer.i18n.Currency
import java.math.BigDecimal

data class CoinInfoDataRO(
    val symbol: String,
    val totalTradedQuoteAssetVolume: String,
    val price: String,
    val priceChangePercent: FormattedText,
    val coinIconUrl: String
) {
    data class FormattedText(
        val text: String,
        val color: Color,
    )
}

internal fun CoinInfoData.toRO(
    currency: Currency,
    exchangeRate: BigDecimal
): CoinInfoDataRO {
    val formattedTextPriceChange = if (priceChangePercent >= BigDecimal.ZERO) {
        CoinInfoDataRO.FormattedText(
            text = "+$priceChangePercent%",
            color = Color.Green
        )
    } else {
        CoinInfoDataRO.FormattedText(
            text = "$priceChangePercent%",
            color = Color.Red
        )
    }

    return CoinInfoDataRO(
        symbol = symbol,
        totalTradedQuoteAssetVolume = formatCurrency(
            totalTradedQuoteAssetVolume * exchangeRate,
            currency
        ),
        price = formatCurrency(
            price * exchangeRate,
            currency
        ),
        priceChangePercent = formattedTextPriceChange,
        coinIconUrl = coinIconUrl
    )
}

private fun formatCurrency(
    value: BigDecimal,
    currency: Currency
): String {
    return "${currency.prefixSign}${currency.decimalFormat.format(value)}"
}
