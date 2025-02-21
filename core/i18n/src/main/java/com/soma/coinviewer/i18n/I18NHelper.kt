package com.soma.coinviewer.i18n

import kotlinx.coroutines.channels.Channel

interface I18NHelper {
    val i18NEventBus: Channel<I18NEvent>
    suspend fun saveRegion(selectedRegion: SelectedRegion): Result<Unit>
    suspend fun getRegion(): Result<SelectedRegion>
}

sealed class I18NEvent {
    data object UpdateLanguage : I18NEvent()
}
