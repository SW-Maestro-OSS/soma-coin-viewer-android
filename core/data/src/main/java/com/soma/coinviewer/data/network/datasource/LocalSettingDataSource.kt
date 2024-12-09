package com.soma.coinviewer.data.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.soma.coinviewer.data.preferences.PreferenceKeys
import com.soma.coinviewer.domain.preferences.PriceCurrencyUnit
import com.soma.coinviewer.domain.preferences.Language
import com.soma.coinviewer.domain.preferences.HowToShowSymbols
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalSettingDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    // 데이터 저장
    suspend fun savePriceCurrencyUnit(priceCurrencyUnit: PriceCurrencyUnit) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.PRICE_CURRENCY_UNIT.key] = priceCurrencyUnit.value
        }
    }

    suspend fun saveLanguage(language: Language) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.LANGUAGE.key] = language.value
        }
    }

    suspend fun saveHowToShowSymbols(howToShowSymbols: HowToShowSymbols) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.HOW_TO_SHOW_SYMBOLS.key] = howToShowSymbols.value
        }
    }

    // 데이터 조회
    fun getPriceCurrencyUnit(): Flow<PriceCurrencyUnit> {
        return dataStore.data.map { preferences ->
            val value = preferences[PreferenceKeys.PRICE_CURRENCY_UNIT.key]
            PriceCurrencyUnit.fromValue(value)
        }
    }

    fun getLanguage(): Flow<Language> {
        return dataStore.data.map { preferences ->
            val value = preferences[PreferenceKeys.LANGUAGE.key]
            Language.fromValue(value)
        }
    }

    fun getHowToShowSymbols(): Flow<HowToShowSymbols> {
        return dataStore.data.map { preferences ->
            val value = preferences[PreferenceKeys.HOW_TO_SHOW_SYMBOLS.key]
            HowToShowSymbols.fromValue(value)
        }
    }
}