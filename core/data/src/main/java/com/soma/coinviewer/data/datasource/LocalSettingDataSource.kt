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
    // ✅ 데이터 저장
    suspend fun savePriceCurrencyUnit(priceCurrencyUnit: PriceCurrencyUnit) {
        dataStore.savePreference(PreferenceKeys.PRICE_CURRENCY_UNIT.key, priceCurrencyUnit) { it.value }
    }

    suspend fun saveLanguage(language: Language) {
        dataStore.savePreference(PreferenceKeys.LANGUAGE.key, language) { it.value }
    }

    suspend fun saveHowToShowSymbols(howToShowSymbols: HowToShowSymbols) {
        dataStore.savePreference(PreferenceKeys.HOW_TO_SHOW_SYMBOLS.key, howToShowSymbols) { it.value }
    }

    // ✅ 데이터 조회
    fun getPriceCurrencyUnit(): Flow<PriceCurrencyUnit> {
        return dataStore.getPreference(PreferenceKeys.PRICE_CURRENCY_UNIT.key) { value ->
            PriceCurrencyUnit.fromValue(value)
        }
    }

    fun getLanguage(): Flow<Language> {
        return dataStore.getPreference(PreferenceKeys.LANGUAGE.key) { value ->
            Language.fromValue(value)
        }
    }

    fun getHowToShowSymbols(): Flow<HowToShowSymbols> {
        return dataStore.getPreference(PreferenceKeys.HOW_TO_SHOW_SYMBOLS.key) { value ->
            HowToShowSymbols.fromValue(value)
        }
    }
}

// ✅ 함수 재사용을 위한 확장 함수 정의
private suspend fun <T> DataStore<Preferences>.savePreference(
    key: Preferences.Key<String>,
    value: T,
    transform: (T) -> String
) {
    edit { preferences ->
        preferences[key] = transform(value)
    }
}

private fun <T> DataStore<Preferences>.getPreference(
    key: Preferences.Key<String>,
    transform: (String?) -> T
): Flow<T> {
    return data.map { preferences ->
        val value = preferences[key]
        transform(value)
    }
}