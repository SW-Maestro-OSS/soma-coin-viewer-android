package com.soma.coinviewer.data.datastore.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.soma.coinviewer.data.datastore.util.getPreference
import com.soma.coinviewer.data.datastore.util.savePreference
import com.soma.coinviewer.domain.preferences.HowToShowSymbols
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalSettingDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun setHowToShowSymbols(howToShowSymbols: HowToShowSymbols) {
        dataStore.savePreference(
            SettingPreferenceKeys.HOW_TO_SHOW_SYMBOLS.key,
            howToShowSymbols
        ) { it.value }
    }

    fun getHowToShowSymbols(): Flow<HowToShowSymbols> {
        return dataStore.getPreference(SettingPreferenceKeys.HOW_TO_SHOW_SYMBOLS.key) { value ->
            HowToShowSymbols.fromValue(value)
        }
    }
}

enum class SettingPreferenceKeys(val key: Preferences.Key<String>) {
    HOW_TO_SHOW_SYMBOLS(stringPreferencesKey("how_to_show_symbols")),
}
