package com.soma.coinviewer.data.repository

import com.soma.coinviewer.data.datastore.datasource.LocalSettingDataSource
import com.soma.coinviewer.domain.preferences.HowToShowSymbols
import com.soma.coinviewer.domain.repository.SettingRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val localSettingDataSource: LocalSettingDataSource
) : SettingRepository {
    override suspend fun saveHowToShowSymbols(howToShowSymbols: HowToShowSymbols) {
        localSettingDataSource.setHowToShowSymbols(howToShowSymbols)
    }

    override suspend fun getHowToShowSymbols(): HowToShowSymbols =
        localSettingDataSource.getHowToShowSymbols().first()
}
