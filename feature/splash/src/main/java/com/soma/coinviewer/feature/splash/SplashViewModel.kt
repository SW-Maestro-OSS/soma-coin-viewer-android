package com.soma.coinviewer.feature.splash

import android.icu.util.TimeZone
import androidx.lifecycle.viewModelScope
import com.soma.coinviewer.common_ui.base.BaseViewModel
import com.soma.coinviewer.domain.repository.ExchangeRateRepository
import com.soma.coinviewer.i18n.Currency
import com.soma.coinviewer.i18n.I18NHelper
import com.soma.coinviewer.i18n.SelectedRegion
import com.soma.coinviewer.i18n.USDCurrency
import com.soma.coinviewer.i18n.koreanCurrency
import com.soma.coinviewer.navigation.DeepLinkRoute
import com.soma.coinviewer.navigation.NavigationHelper
import com.soma.coinviewer.navigation.NavigationTarget
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val exchangeRateRepository: ExchangeRateRepository,
    private val i18NHelper: I18NHelper,
    private val exceptionHandler: CoroutineExceptionHandler,
    private val navigationHelper: NavigationHelper,
) : BaseViewModel() {

    init {
        viewModelScope.launch {
            val exchangeRateJob = getExchangeRate()
            val regionInitJob = initializeRegion()

            delay(2000L)
            exchangeRateJob.join()
            regionInitJob.join()

            navigationHelper.navigateTo(
                NavigationTarget(
                    destination = DeepLinkRoute.Home,
                    popUpTo = R.id.splashFragment
                )
            )
        }
    }

    private fun getExchangeRate() = viewModelScope.launch(exceptionHandler) {
        exchangeRateRepository.updateExchangeRate()
    }

    private fun initializeRegion() = viewModelScope.launch(exceptionHandler) {
        val selectedRegion = runCatching {
            i18NHelper.getRegion()
        }.getOrDefault(getCurrentRegion())

        i18NHelper.saveRegion(selectedRegion)
    }

    private fun getCurrentRegion(): SelectedRegion {
        val currentLocale = Locale.getDefault()
        val currentTimeZone = TimeZone.getDefault()
        val defaultCurrency = getCurrencyForLocale(currentLocale)

        return SelectedRegion(
            locale = currentLocale,
            timezoneId = currentTimeZone.id,
            language = currentLocale,
            currency = defaultCurrency
        )
    }

    private fun getCurrencyForLocale(locale: Locale): Currency {
        return when (locale.country) {
            "KR" -> koreanCurrency
            else -> USDCurrency
        }
    }
}
