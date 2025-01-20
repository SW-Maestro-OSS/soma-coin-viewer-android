package com.soma.coinviewer.i18n

import java.util.Locale

data class SelectedRegion(
    val locale: Locale,
    val timezoneId: String,
    val language: Locale,
    val currency: Currency,
)
