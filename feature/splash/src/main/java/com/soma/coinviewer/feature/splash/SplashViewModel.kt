package com.soma.coinviewer.feature.splash

import android.icu.util.TimeZone
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.soma.coinviewer.common_ui.base.BaseViewModel
import com.soma.coinviewer.domain.repository.ExchangeRateRepository
import com.soma.coinviewer.i18n.Currency
import com.soma.coinviewer.i18n.SelectedRegion
import com.soma.coinviewer.i18n.datasource.SelectedI18NDataSource
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
    private val selectedI18NDataSource: SelectedI18NDataSource,
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
            selectedI18NDataSource.getRegion()
        }.getOrDefault(getCurrentRegion())

        Log.d("test", selectedRegion.toString())
        selectedI18NDataSource.saveRegion(selectedRegion)
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
            "KR" -> Currency(prefixSign = "₩", postUnit = "KRW")
            "US" -> Currency(prefixSign = "$", postUnit = "USD")
            else -> Currency(prefixSign = "₩", postUnit = "KRW")
        }
    }
}
