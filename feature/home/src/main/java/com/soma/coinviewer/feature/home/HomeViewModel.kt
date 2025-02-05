package com.soma.coinviewer.feature.home

import androidx.lifecycle.viewModelScope
import com.soma.coinviewer.common_ui.base.BaseViewModel
import com.soma.coinviewer.domain.preferences.HowToShowSymbols
import com.soma.coinviewer.domain.repository.CoinInfoRepository
import com.soma.coinviewer.domain.repository.ExchangeRateRepository
import com.soma.coinviewer.domain.repository.SettingRepository
import com.soma.coinviewer.feature.home.ro.toRO
import com.soma.coinviewer.i18n.I18NHelper
import com.soma.coinviewer.i18n.USDCurrency
import com.soma.coinviewer.navigation.NavigationHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
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
) : BaseViewModel() {

    private val _currency = MutableStateFlow(USDCurrency)
    val currency = _currency.asStateFlow()

    private var exchangeRate: BigDecimal = BigDecimal.ONE

    private val _listSortType = MutableStateFlow(ListSortType.TOTAL_TRADE)
    internal val listSortType = _listSortType.asStateFlow()

    private val baseCoinData = coinInfoRepository.coinInfoData
        .onEach { delay(200L) }
        .map { data -> data.map { it.value }.toList() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    internal val totalTradeData = baseCoinData
        .map { coinInfos ->
            coinInfos.sortedByDescending { it.totalTradedQuoteAssetVolume }
                .take(COIN_INFO_TICKER_DATA_MAX_SIZE)
                .map { it.toRO(currency.value, exchangeRate) }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    internal val symbolAscData = baseCoinData
        .map { coinInfos ->
            coinInfos.sortedBy { it.symbol }
                .take(COIN_INFO_TICKER_DATA_MAX_SIZE)
                .map { it.toRO(currency.value, exchangeRate) }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    internal val symbolDescData = baseCoinData
        .map { coinInfos ->
            coinInfos.sortedByDescending { it.symbol }
                .take(COIN_INFO_TICKER_DATA_MAX_SIZE)
                .map { it.toRO(currency.value, exchangeRate) }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    internal val priceAscData = baseCoinData
        .map { coinInfos ->
            coinInfos.sortedBy { it.price }
                .take(COIN_INFO_TICKER_DATA_MAX_SIZE)
                .map { it.toRO(currency.value, exchangeRate) }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    internal val priceDescData = baseCoinData
        .map { coinInfos ->
            coinInfos.sortedByDescending { it.price }
                .take(COIN_INFO_TICKER_DATA_MAX_SIZE)
                .map { it.toRO(currency.value, exchangeRate) }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    internal val priceChangeAscData = baseCoinData
        .map { coinInfos ->
            coinInfos.sortedBy { it.priceChangePercent }
                .take(COIN_INFO_TICKER_DATA_MAX_SIZE)
                .map { it.toRO(currency.value, exchangeRate) }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    internal val priceChangeDescData = baseCoinData
        .map { coinInfos ->
            coinInfos.sortedByDescending { it.priceChangePercent }
                .take(COIN_INFO_TICKER_DATA_MAX_SIZE)
                .map { it.toRO(currency.value, exchangeRate) }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    private val _howToShowSymbols = MutableStateFlow<HowToShowSymbols>(HowToShowSymbols.DEFAULT)
    internal val howToShowSymbols = _howToShowSymbols.asStateFlow()

    internal fun initExchangeRate() = viewModelScope.launch {
        _currency.value = i18NHelper.getRegion().currency
        exchangeRate = when (currency.value) {
            USDCurrency -> BigDecimal.ONE
            else -> exchangeRateRepository.getExchangeRate("USD")
        }
    }

    internal fun loadHowToShowSymbols() = viewModelScope.launch {
        _howToShowSymbols.value = settingRepository.getHowToShowSymbols()
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
