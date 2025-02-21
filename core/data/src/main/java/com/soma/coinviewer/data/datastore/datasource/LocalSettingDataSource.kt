package com.soma.coinviewer.data.datastore.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.soma.coinviewer.data.datastore.util.getPreference
import com.soma.coinviewer.data.datastore.util.savePreference
import com.soma.coinviewer.domain.preferences.HowToShowSymbols
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class LocalSettingDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun setHowToShowSymbols(howToShowSymbols: HowToShowSymbols): Result<Unit> =
        runCatching {
            dataStore.savePreference(
                SettingPreferenceKeys.HOW_TO_SHOW_SYMBOLS.key,
                howToShowSymbols
            ) { it.value }
        }

    suspend fun getHowToShowSymbols(): Result<HowToShowSymbols> = runCatching {
        dataStore.getPreference(SettingPreferenceKeys.HOW_TO_SHOW_SYMBOLS.key) { value ->
            HowToShowSymbols.fromValue(value)
        }.first()
    }
}

enum class SettingPreferenceKeys(val key: Preferences.Key<String>) {
    HOW_TO_SHOW_SYMBOLS(stringPreferencesKey("how_to_show_symbols")),
}
