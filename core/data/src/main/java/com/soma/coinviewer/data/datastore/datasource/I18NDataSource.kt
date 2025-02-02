package com.soma.coinviewer.data.datastore.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.soma.coinviewer.i18n.I18NHelper
import com.soma.coinviewer.i18n.SelectedRegion
import com.soma.coinviewer.i18n.USDCurrency
import com.soma.coinviewer.i18n.koreanCurrency
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.Locale
import javax.inject.Inject

class I18NDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : I18NHelper {
    override suspend fun saveRegion(selectedRegion: SelectedRegion) {
        val currency = when {
            selectedRegion.currency.prefixSign == "₩" &&
                    selectedRegion.currency.postUnit == "원" -> "KRW"

            selectedRegion.currency.prefixSign == "$" &&
                    selectedRegion.currency.postUnit == "dollars" -> "USD"

            else -> throw IllegalArgumentException("Unsupported currency type")
        }

        val regionString = "SelectedRegion(" +
                "locale=${selectedRegion.locale}," +
                "timezoneId=${selectedRegion.timezoneId}," +
                "language=${selectedRegion.language}," +
                "currency=$currency" +
                ")"

        dataStore.edit { preferences ->
            preferences[SELECTED_REGION_KEY] = regionString
        }
    }

    override suspend fun getRegion(): SelectedRegion = dataStore.data.map { preferences ->
        val data = preferences[SELECTED_REGION_KEY]
        data?.let {
            val properties = it
                .removePrefix("SelectedRegion(")
                .removeSuffix(")")
                .split(",")
                .associate { property ->
                    val (key, value) = property.split("=", limit = 2)
                    key to value
                }

            val currency = when (properties["currency"]) {
                "KRW" -> koreanCurrency
                "USD" -> USDCurrency
                else -> throw IllegalArgumentException("Invalid currency type: ${properties["currency"]}")
            }

            val locale = Locale(
                properties["locale"]
                    ?: throw NullPointerException("locale이 유효하지 않습니다.")
            )

            SelectedRegion(
                locale = locale,
                timezoneId = properties["timezoneId"]
                    ?: throw NullPointerException("timezoneId가 유효하지 않습니다."),
                language = Locale(
                    properties["language"]
                        ?: throw NullPointerException("language가 유효하지 않습니다.")
                ),
                currency = currency
            )
        } ?: throw NullPointerException("SelectedRegion 데이터를 찾을 수 없습니다.")
    }.first()

    private companion object {
        val SELECTED_REGION_KEY = stringPreferencesKey("selectedRegion")
    }
}
