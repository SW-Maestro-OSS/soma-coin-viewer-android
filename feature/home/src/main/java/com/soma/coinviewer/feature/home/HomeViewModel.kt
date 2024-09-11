package com.soma.coinviewer.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soma.coinviewer.domain.entity.BinanceOrderBookMessage
import com.soma.coinviewer.domain.entity.BinanceOrderBookParams
import com.soma.coinviewer.domain.repository.BinanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val binanceRepository: BinanceRepository
) : ViewModel() {
    private val _homeUiState = MutableStateFlow<String>("")
    val homeUiState: StateFlow<String> get() = _homeUiState


    fun testBinance() {
        binanceRepository.connect()

        val messageSample = BinanceOrderBookMessage(
            id = "51e2affb-0aba-4821-ba75-f2625006eb43",
            method = "depth",
            params = BinanceOrderBookParams(
                symbol = "BNBBTC",
                limit = 5
            )
        )
        viewModelScope.launch {
            binanceRepository.sendMessage(messageSample)
        }
    }

    fun disconnectBinance() {
        binanceRepository.disconnect()
    }
}