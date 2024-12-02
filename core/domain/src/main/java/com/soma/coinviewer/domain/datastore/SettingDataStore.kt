package com.soma.coinviewer.domain.datastore

import kotlinx.coroutines.flow.Flow

interface SettingDataStore {
    suspend fun savePriceCurrencyUnit(priceCurrencyUnit: PriceCurrencyUnit)
    suspend fun saveLanguage(language: Language)
    suspend fun saveHowToShowSymbols(howToShowSymbols: HowToShowSymbols)

    fun getPriceCurrencyUnit(): Flow<PriceCurrencyUnit>
    fun getLanguage(): Flow<Language>
    fun getHowToShowSymbols(): Flow<HowToShowSymbols>
}