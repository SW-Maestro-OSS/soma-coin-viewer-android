package com.soma.coinviewer.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.soma.coinviewer.domain.datastore.PriceCurrencyUnit
import com.soma.coinviewer.domain.datastore.Language
import com.soma.coinviewer.domain.datastore.HowToShowSymbols
import com.soma.coinviewer.domain.datastore.SettingDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SettingDataSource {
    // 데이터 저장
    override suspend fun savePriceCurrencyUnit(priceCurrencyUnit: PriceCurrencyUnit) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.PRICE_CURRENCY_UNIT.key] = priceCurrencyUnit.value
        }
    }

    override suspend fun saveLanguage(language: Language) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.LANGUAGE.key] = language.value
        }
    }

    override suspend fun saveHowToShowSymbols(howToShowSymbols: HowToShowSymbols) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.HOW_TO_SHOW_SYMBOLS.key] = howToShowSymbols.value
        }
    }

    // 데이터 조회
    override fun getPriceCurrencyUnit(): Flow<PriceCurrencyUnit> {
        return dataStore.data.map { preferences ->
            val value = preferences[PreferenceKeys.PRICE_CURRENCY_UNIT.key]
            PriceCurrencyUnit.fromValue(value)
        }
    }

    override fun getLanguage(): Flow<Language> {
        return dataStore.data.map { preferences ->
            val value = preferences[PreferenceKeys.LANGUAGE.key]
            Language.fromValue(value)
        }
    }

    override fun getHowToShowSymbols(): Flow<HowToShowSymbols> {
        return dataStore.data.map { preferences ->
            val value = preferences[PreferenceKeys.HOW_TO_SHOW_SYMBOLS.key]
            HowToShowSymbols.fromValue(value)
        }
    }
}