package com.soma.coinviewer.presentation

import androidx.lifecycle.viewModelScope
import com.soma.coinviewer.common_ui.base.BaseViewModel
import com.soma.coinviewer.domain.model.exception.ErrorHelper
import com.soma.coinviewer.domain.repository.CoinInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val coinInfoRepository: CoinInfoRepository,
    internal val errorHelper: ErrorHelper,
) : BaseViewModel() {
    internal fun connectWebSocket() = viewModelScope.launch {
        coinInfoRepository.connect()
            .onFailure { errorHelper.sendError(it) }

        coinInfoRepository.subscribeWebSocketData()
            .onFailure { errorHelper.sendError(it) }
    }

    internal fun disconnectWebsocket() = viewModelScope.launch {
        coinInfoRepository.disconnect()
            .onFailure { errorHelper.sendError(it) }
    }
}
