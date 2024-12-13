package com.soma.coinviewer.data.datastore.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.soma.coinviewer.data.datastore.util.getPreference
import com.soma.coinviewer.data.datastore.util.savePreference
import com.soma.coinviewer.domain.preferences.HowToShowSymbols
import com.soma.coinviewer.domain.preferences.Language
import com.soma.coinviewer.domain.preferences.CurrencyCode
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalSettingDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun savePriceCurrencyUnit(currencyCode: CurrencyCode) {
        dataStore.savePreference(
            SettingPreferenceKeys.PRICE_CURRENCY_UNIT.key,
            currencyCode
        ) { it.value }
    }

    suspend fun saveLanguage(language: Language) {
        dataStore.savePreference(SettingPreferenceKeys.LANGUAGE.key, language) { it.value }
    }

    suspend fun saveHowToShowSymbols(howToShowSymbols: HowToShowSymbols) {
        dataStore.savePreference(
            SettingPreferenceKeys.HOW_TO_SHOW_SYMBOLS.key,
            howToShowSymbols
        ) { it.value }
    }

    fun getPriceCurrencyUnit(): Flow<CurrencyCode> {
        return dataStore.getPreference(SettingPreferenceKeys.PRICE_CURRENCY_UNIT.key) { value ->
            CurrencyCode.fromValue(value)
        }
    }

    fun getLanguage(): Flow<Language> {
        return dataStore.getPreference(SettingPreferenceKeys.LANGUAGE.key) { value ->
            Language.fromValue(value)
        }
    }

    fun getHowToShowSymbols(): Flow<HowToShowSymbols> {
        return dataStore.getPreference(SettingPreferenceKeys.HOW_TO_SHOW_SYMBOLS.key) { value ->
            HowToShowSymbols.fromValue(value)
        }
    }
}

enum class SettingPreferenceKeys(val key: Preferences.Key<String>) {
    PRICE_CURRENCY_UNIT(stringPreferencesKey("price_currency_unit")),
    LANGUAGE(stringPreferencesKey("language")),
    HOW_TO_SHOW_SYMBOLS(stringPreferencesKey("how_to_show_symbols")),
}
