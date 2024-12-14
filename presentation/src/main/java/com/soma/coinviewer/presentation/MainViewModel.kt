package com.soma.coinviewer.presentation

import androidx.lifecycle.viewModelScope
import com.soma.coinviewer.common_ui.base.BaseViewModel
import com.soma.coinviewer.domain.model.exception.ErrorHelper
import com.soma.coinviewer.domain.repository.BinanceRepository
import com.soma.coinviewer.navigation.NavigationHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val binanceRepository: BinanceRepository,
    private val navigationHelper: NavigationHelper,
    private val errorHelper: ErrorHelper,
) : BaseViewModel() {
    internal val navigationFlow = navigationHelper.navigationFlow
    internal val errorFlow = errorHelper.errorEvent

    internal fun connectWebSocket() = viewModelScope.launch {
        binanceRepository.connect()
        binanceRepository.subscribeWebSocketData()
    }

    internal fun disconnectWebsocket() = viewModelScope.launch {
        binanceRepository.disconnect()
    }
}