package com.soma.coinviewer.data.datastore.preferences

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey

enum class PreferenceKeys(val key: Preferences.Key<String>) {
    PRICE_CURRENCY_UNIT(stringPreferencesKey("price_currency_unit")),
    LANGUAGE(stringPreferencesKey("language")),
    HOW_TO_SHOW_SYMBOLS(stringPreferencesKey("how_to_show_symbols")),
}