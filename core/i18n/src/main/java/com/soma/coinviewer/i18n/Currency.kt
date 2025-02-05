package com.soma.coinviewer.i18n

import java.text.NumberFormat
import java.util.Locale

data class Currency(
    val prefixSign: String,
    val postUnit: String,
    val decimalFormat: NumberFormat,
)

val koreanCurrency = Currency(
    prefixSign = "₩",
    postUnit = "원",
    decimalFormat = NumberFormat.getInstance(Locale.KOREA).apply {
        maximumFractionDigits = 2
    }
)

val USDCurrency = Currency(
    prefixSign = "$",
    postUnit = "dollars",
    decimalFormat = NumberFormat.getInstance(Locale.US).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 4
    }
)
