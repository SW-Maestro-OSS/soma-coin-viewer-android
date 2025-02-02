package com.soma.coinviewer.i18n

interface I18NHelper {
    suspend fun saveRegion(selectedRegion: SelectedRegion)
    suspend fun getRegion(): SelectedRegion
}
