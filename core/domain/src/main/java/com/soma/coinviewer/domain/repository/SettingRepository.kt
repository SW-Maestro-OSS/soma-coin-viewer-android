package com.soma.coinviewer.domain.repository

import com.soma.coinviewer.domain.datastore.HowToShowSymbols
import com.soma.coinviewer.domain.datastore.Language
import com.soma.coinviewer.domain.datastore.PriceCurrencyUnit
import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    suspend fun savePriceCurrencyUnit(priceCurrencyUnit: PriceCurrencyUnit)
    suspend fun saveLanguage(language: Language)
    suspend fun saveHowToShowSymbols(howToShowSymbols: HowToShowSymbols)

    fun getPriceCurrencyUnit(): Flow<PriceCurrencyUnit>
    fun getLanguage(): Flow<Language>
    fun getHowToShowSymbols(): Flow<HowToShowSymbols>
}