package com.soma.coinviewer.feature.splash

import androidx.lifecycle.viewModelScope
import com.soma.coinviewer.common_ui.base.BaseViewModel
import com.soma.coinviewer.domain.repository.ExchangeRateRepository
import com.soma.coinviewer.navigation.DeepLinkRoute
import com.soma.coinviewer.navigation.NavigationHelper
import com.soma.coinviewer.navigation.NavigationTarget
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val exchangeRateRepository: ExchangeRateRepository,
    private val exceptionHandler: CoroutineExceptionHandler,
    private val navigationHelper: NavigationHelper,
) : BaseViewModel() {
    init {
        viewModelScope.launch {
            val exchangeRateJob = getExchangeRate()

            delay(2000L) // 최소 2초는 기다립니다.
            exchangeRateJob.cancelAndJoin()

            navigationHelper.navigateTo(
                NavigationTarget(
                    destination = DeepLinkRoute.Home,
                    popUpTo = R.id.splashFragment
                )
            )
        }
    }

    private fun getExchangeRate() = viewModelScope.launch(exceptionHandler) {
        exchangeRateRepository.getExchangeRate()
    }
}