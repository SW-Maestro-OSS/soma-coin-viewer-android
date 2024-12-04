package com.soma.coinviewer.presentation

import androidx.lifecycle.viewModelScope
import com.soma.coinviewer.common_ui.base.BaseViewModel
import com.soma.coinviewer.domain.repository.BinanceRepository
import com.soma.coinviewer.domain.repository.ExchangeRateRepository
import com.soma.coinviewer.feature.splash.R
import com.soma.coinviewer.navigation.DeepLinkRoute
import com.soma.coinviewer.navigation.NavigationHelper
import com.soma.coinviewer.navigation.NavigationTarget
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val exchangeRateRepository: ExchangeRateRepository,
    private val binanceRepository: BinanceRepository,
    private val navigationHelper: NavigationHelper,
) : BaseViewModel() {
    val navigationFlow = navigationHelper.navigationFlow

    init {
        viewModelScope.launch {
            val subscribeJob = connectWebSocket()
            getExchangeRate()
            delay(2000L) // 최소 2초는 기다립니다.
            subscribeJob.cancelAndJoin() // 2초가 되어도 연결이 안됐을경우 이를 기다립니다.
            navigationHelper.navigateTo(
                NavigationTarget(
                    destination = DeepLinkRoute.Home,
                    popUpTo = R.id.splashFragment
                )
            )
        }
    }

    internal fun connectWebSocket() = viewModelScope.launch {
        binanceRepository.connect()
        val subscribeJob = launch { binanceRepository.connect() }
        subscribeJob.cancelAndJoin()
    }

    internal fun disconnectWebsocket() = viewModelScope.launch {
        binanceRepository.disconnect()
    }

    private fun getExchangeRate() = viewModelScope.launch {
        exchangeRateRepository.getExchangeRate()
    }
}