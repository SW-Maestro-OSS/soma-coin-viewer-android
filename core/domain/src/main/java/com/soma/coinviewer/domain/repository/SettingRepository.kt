package com.soma.coinviewer.domain.repository

import com.soma.coinviewer.domain.preferences.HowToShowSymbols
import com.soma.coinviewer.domain.preferences.Language
import com.soma.coinviewer.domain.preferences.PriceCurrencyUnit
import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    suspend fun savePriceCurrencyUnit(priceCurrencyUnit: PriceCurrencyUnit)
    suspend fun saveLanguage(language: Language)
    suspend fun saveHowToShowSymbols(howToShowSymbols: HowToShowSymbols)

    fun getPriceCurrencyUnit(): Flow<PriceCurrencyUnit>
    fun getLanguage(): Flow<Language>
    fun getHowToShowSymbols(): Flow<HowToShowSymbols>
}