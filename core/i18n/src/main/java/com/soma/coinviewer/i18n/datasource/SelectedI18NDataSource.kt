package com.soma.coinviewer.i18n.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.soma.coinviewer.i18n.Currency
import com.soma.coinviewer.i18n.SelectedRegion
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.Locale
import javax.inject.Inject

class SelectedI18NDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun saveRegion(selectedRegion: SelectedRegion) {
        dataStore.edit { preferences ->
            preferences[SELECTED_REGION_KEY] = selectedRegion.toString()
        }
    }

    suspend fun getRegion(): SelectedRegion = dataStore.data.map { preferences ->
        val data = preferences[SELECTED_REGION_KEY]
        data?.let {
            val properties = it
                .removePrefix("SelectedRegion(")
                .removeSuffix(")")
                .split(", ")
                .associate {
                    val (key, value) = it.split("=")
                    key to value
                }

            SelectedRegion(
                locale = Locale(
                    properties["locale"]
                        ?: throw NullPointerException("locale이 유효하지 않습니다.")
                ),
                timezoneId = properties["timezoneId"]
                    ?: throw NullPointerException("timezoneId가 유효하지 않습니다."),
                language = Locale(
                    properties["language"]
                        ?: throw NullPointerException("language가 유효하지 않습니다.")
                ),
                currency = Currency(
                    prefixSign = properties["currency.prefixSign"]
                        ?: throw NullPointerException("currency.prefixSign이 유효하지 않습니다."),
                    postUnit = properties["currency.postUnit"]
                        ?: throw NullPointerException("currency.postUnit이 유효하지 않습니다.")
                )
            )
        } ?: SelectedRegion(
            locale = Locale.KOREA,
            timezoneId = "Asia/Seoul",
            language = Locale.KOREAN,
            currency = Currency(
                prefixSign = "₩",
                postUnit = "KRW"
            )
        )
    }.first()

    private companion object {
        val SELECTED_REGION_KEY = stringPreferencesKey("selectedRegion")
    }
}