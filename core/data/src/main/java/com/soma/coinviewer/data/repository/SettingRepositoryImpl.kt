package com.soma.coinviewer.data.repository

import com.soma.coinviewer.data.datastore.LocalSettingDataSource
import com.soma.coinviewer.domain.datastore.HowToShowSymbols
import com.soma.coinviewer.domain.datastore.Language
import com.soma.coinviewer.domain.datastore.PriceCurrencyUnit
import com.soma.coinviewer.domain.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val localDataSource: LocalSettingDataSource
) : SettingRepository {
    override suspend fun savePriceCurrencyUnit(priceCurrencyUnit: PriceCurrencyUnit) {
        localDataSource.savePriceCurrencyUnit(priceCurrencyUnit)
    }

    override suspend fun saveLanguage(language: Language) {
        localDataSource.saveLanguage(language)
    }

    override suspend fun saveHowToShowSymbols(howToShowSymbols: HowToShowSymbols) {
        localDataSource.saveHowToShowSymbols(howToShowSymbols)
    }

    override fun getPriceCurrencyUnit(): Flow<PriceCurrencyUnit> {
        return localDataSource.getPriceCurrencyUnit()
    }

    override fun getLanguage(): Flow<Language> {
        return localDataSource.getLanguage()
    }

    override fun getHowToShowSymbols(): Flow<HowToShowSymbols> {
        return localDataSource.getHowToShowSymbols()
    }
}