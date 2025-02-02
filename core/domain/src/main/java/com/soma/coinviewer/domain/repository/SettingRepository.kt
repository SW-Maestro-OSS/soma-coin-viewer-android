package com.soma.coinviewer.domain.repository

import com.soma.coinviewer.domain.preferences.HowToShowSymbols

interface SettingRepository {
    suspend fun saveHowToShowSymbols(howToShowSymbols: HowToShowSymbols)
    suspend fun getHowToShowSymbols(): HowToShowSymbols
}
