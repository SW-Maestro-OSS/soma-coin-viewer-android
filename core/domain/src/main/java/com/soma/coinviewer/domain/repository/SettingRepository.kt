package com.soma.coinviewer.domain.repository

import com.soma.coinviewer.domain.preferences.HowToShowSymbols
import com.soma.coinviewer.domain.preferences.Language
import com.soma.coinviewer.domain.preferences.CurrencyCode
import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    suspend fun savePriceCurrencyUnit(currencyCode: CurrencyCode)
    suspend fun saveLanguage(language: Language)
    suspend fun saveHowToShowSymbols(howToShowSymbols: HowToShowSymbols)

    fun getPriceCurrencyUnit(): Flow<CurrencyCode>
    fun getLanguage(): Flow<Language>
    fun getHowToShowSymbols(): Flow<HowToShowSymbols>
}