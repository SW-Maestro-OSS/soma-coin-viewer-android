package com.soma.coinviewer.feature.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.soma.coinviewer.common_ui.base.BaseViewModel
import com.soma.coinviewer.domain.entity.BinanceMessage
import com.soma.coinviewer.domain.entity.OrderBookParams
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
    private val _homeUiState = MutableStateFlow<String>("")
    val homeUiState: StateFlow<String> get() = _homeUiState


    fun testBinance() {
        binanceRepository.connect()

        val messageSample = BinanceMessage(
            id = "51e2affb-0aba-4821-ba75-f2625006eb43",
            method = "depth",
            params = OrderBookParams(
                symbol = "BNBBTC",
                limit = 5
            )
        )

        viewModelScope.launch(exceptionHandler) {
            try {
                val response = binanceRepository.sendMessage(messageSample)
                _homeUiState.value = response ?: "No response"
            } finally {
                Log.d("Binance", "Binance Success!!")
            }
        }
    }

    fun testDisconnectBinance() {
        binanceRepository.disconnect()
    }
}