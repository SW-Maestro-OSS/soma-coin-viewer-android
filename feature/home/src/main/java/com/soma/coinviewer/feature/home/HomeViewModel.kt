package com.soma.coinviewer.feature.home

import androidx.lifecycle.viewModelScope
import com.soma.coinviewer.common_ui.base.BaseViewModel
import com.soma.coinviewer.domain.model.exception.ErrorHelper
import com.soma.coinviewer.domain.preferences.HowToShowSymbols
import com.soma.coinviewer.domain.repository.CoinInfoRepository
import com.soma.coinviewer.domain.repository.ExchangeRateRepository
import com.soma.coinviewer.domain.repository.SettingRepository
import com.soma.coinviewer.feature.home.ro.toRO
import com.soma.coinviewer.i18n.Currency
import com.soma.coinviewer.i18n.I18NHelper
import com.soma.coinviewer.i18n.USDCurrency
import com.soma.coinviewer.navigation.NavigationHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val coinInfoRepository: CoinInfoRepository,
    private val exchangeRateRepository: ExchangeRateRepository,
    private val settingRepository: SettingRepository,
    internal val navigationHelper: NavigationHelper,
    private val i18NHelper: I18NHelper,
    private val errorHelper: ErrorHelper,
) : BaseViewModel() {
    private var currency: Currency = USDCurrency
    private var exchangeRate: BigDecimal = BigDecimal.ONE

    private val _listSortType = MutableStateFlow(ListSortType.TOTAL_TRADE)
    internal val listSortType = _listSortType.asStateFlow()

    private val baseCoinData = coinInfoRepository.coinInfoData
        .onEach { delay(200L) }
        .map { data -> data.map { it.value }.toList() }
        .catch { throwable -> errorHelper.sendError(throwable) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    internal val totalTradeData = baseCoinData
        .map { coinInfos ->
            coinInfos.sortedByDescending { it.totalTradedQuoteAssetVolume }
                .take(COIN_INFO_TICKER_DATA_MAX_SIZE)
                .map { it.toRO(currency, exchangeRate) }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    internal val symbolAscData = baseCoinData
        .map { coinInfos ->
            coinInfos.sortedBy { it.symbol }
                .take(COIN_INFO_TICKER_DATA_MAX_SIZE)
                .map { it.toRO(currency, exchangeRate) }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    internal val symbolDescData = baseCoinData
        .map { coinInfos ->
            coinInfos.sortedByDescending { it.symbol }
                .take(COIN_INFO_TICKER_DATA_MAX_SIZE)
                .map { it.toRO(currency, exchangeRate) }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    internal val priceAscData = baseCoinData
        .map { coinInfos ->
            coinInfos.sortedBy { it.price }
                .take(COIN_INFO_TICKER_DATA_MAX_SIZE)
                .map { it.toRO(currency, exchangeRate) }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    internal val priceDescData = baseCoinData
        .map { coinInfos ->
            coinInfos.sortedByDescending { it.price }
                .take(COIN_INFO_TICKER_DATA_MAX_SIZE)
                .map { it.toRO(currency, exchangeRate) }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    internal val priceChangeAscData = baseCoinData
        .map { coinInfos ->
            coinInfos.sortedBy { it.priceChangePercent }
                .take(COIN_INFO_TICKER_DATA_MAX_SIZE)
                .map { it.toRO(currency, exchangeRate) }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    internal val priceChangeDescData = baseCoinData
        .map { coinInfos ->
            coinInfos.sortedByDescending { it.priceChangePercent }
                .take(COIN_INFO_TICKER_DATA_MAX_SIZE)
                .map { it.toRO(currency, exchangeRate) }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    private val _howToShowSymbols = MutableStateFlow<HowToShowSymbols>(HowToShowSymbols.DEFAULT)
    internal val howToShowSymbols = _howToShowSymbols.asStateFlow()

    internal fun initExchangeRate() = viewModelScope.launch {
        i18NHelper.getRegion()
            .onSuccess {
                currency = it.currency
                exchangeRate = when (currency) {
                    USDCurrency -> BigDecimal.ONE
                    else -> exchangeRateRepository.getExchangeRate("USD")
                        .getOrElse {
                            errorHelper.sendError(it)
                            return@launch
                        }
                }
            }.onFailure {
                errorHelper.sendError(it)
            }
    }

    internal fun loadHowToShowSymbols() = viewModelScope.launch {
        settingRepository.getHowToShowSymbols()
            .onSuccess {
                _howToShowSymbols.value = it
            }.onFailure {
                errorHelper.sendError(it)
            }
    }

    internal fun updateSortType(asc: ListSortType, desc: ListSortType) {
        val currentSortType = _listSortType.value

        _listSortType.value = when (currentSortType) {
            asc -> desc
            desc -> asc
            else -> asc
        }
    }

    companion object {
        private const val COIN_INFO_TICKER_DATA_MAX_SIZE = 30
    }
}

enum class ListSortType {
    TOTAL_TRADE,
    SYMBOL_ASC,
    SYMBOL_DESC,
    PRICE_ASC,
    PRICE_DESC,
    ONE_DAY_CHANGE_ASC,
    ONE_DAY_CHANGE_DESC;
}
