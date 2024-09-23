package com.soma.coinviewer.feature.home

import androidx.lifecycle.viewModelScope
import com.soma.coinviewer.common_ui.base.BaseViewModel
import com.soma.coinviewer.domain.entity.BinanceMessage
import com.soma.coinviewer.domain.repository.BinanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val binanceRepository: BinanceRepository
) : BaseViewModel() {
    private val _homeUiState = MutableStateFlow<String?>("")
    val homeUiState: StateFlow<String?> = _homeUiState


    fun testBinance() {
        binanceRepository.connect()

        val messageSample = BinanceMessage(
            id = 734,
            method = "SUBSCRIBE",
            params = listOf("btcusdt@depth")
        )

        viewModelScope.launch(exceptionHandler) {
            binanceRepository.sendMessage(messageSample)

            binanceRepository.subscribeWebSocketData().collect {
                _homeUiState.value = it
            }
        }
    }

    fun testDisconnectBinance() {
        binanceRepository.disconnect()
    }
}