package com.soma.coinviewer.data.repository

import com.soma.coinviewer.data.datastore.datasource.LocalSettingDataSource
import com.soma.coinviewer.domain.preferences.PriceCurrencyUnit
import com.soma.coinviewer.domain.preferences.HowToShowSymbols
import com.soma.coinviewer.domain.preferences.Language
import com.soma.coinviewer.domain.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val localSettingDataSource: LocalSettingDataSource
) : SettingRepository {
    override suspend fun savePriceCurrencyUnit(priceCurrencyUnit: PriceCurrencyUnit) {
        localSettingDataSource.setCurrencyCode(priceCurrencyUnit)
    }

    override suspend fun saveLanguage(language: Language) {
        localSettingDataSource.setLanguage(language)
    }

    override suspend fun saveHowToShowSymbols(howToShowSymbols: HowToShowSymbols) {
        localSettingDataSource.setHowToShowSymbols(howToShowSymbols)
    }

    override fun getPriceCurrencyUnit(): Flow<PriceCurrencyUnit> {
        return localSettingDataSource.getCurrencyCode()
    }

    override fun getLanguage(): Flow<Language> {
        return localSettingDataSource.getLanguage()
    }

    override fun getHowToShowSymbols(): Flow<HowToShowSymbols> {
        return localSettingDataSource.getHowToShowSymbols()
    }
}