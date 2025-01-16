package com.soma.coinviewer.feature.home

import androidx.lifecycle.viewModelScope
import com.soma.coinviewer.common_ui.base.BaseViewModel
import com.soma.coinviewer.domain.preferences.HowToShowSymbols
import com.soma.coinviewer.domain.repository.CoinInfoRepository
import com.soma.coinviewer.domain.repository.SettingRepository
import com.soma.coinviewer.navigation.NavigationHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val coinInfoRepository: CoinInfoRepository,
    private val settingRepository: SettingRepository,
    internal val navigationHelper: NavigationHelper,
) : BaseViewModel() {
    private val _listSortType = MutableStateFlow(ListSortType.TOTAL_TRADE)
    val listSortType = _listSortType.asStateFlow()

    val coinData = coinInfoRepository.sortedCoinInfoData
        .onEach { delay(200L) }
        .map { list ->
            when (_listSortType.value) {
                ListSortType.TOTAL_TRADE -> list.sortedByDescending { it.totalTradedQuoteAssetVolume }
                ListSortType.SYMBOL_ASC -> list.sortedBy { it.symbol }
                ListSortType.SYMBOL_DESC -> list.sortedByDescending { it.symbol }
                ListSortType.PRICE_ASC -> list.sortedBy { it.price }
                ListSortType.PRICE_DESC -> list.sortedByDescending { it.price }
                ListSortType.ONE_DAY_CHANGE_ASC -> list.sortedBy { it.priceChangePercent }
                ListSortType.ONE_DAY_CHANGE_DESC -> list.sortedByDescending { it.priceChangePercent }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList(),
        )

    val howToShowSymbols = settingRepository.getHowToShowSymbols()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HowToShowSymbols.DEFAULT,
        )

    internal fun updateSortType(asc: ListSortType, desc: ListSortType) {
        val currentSortType = _listSortType.value

        _listSortType.value = when (currentSortType) {
            asc -> desc
            desc -> asc
            else -> asc
        }
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