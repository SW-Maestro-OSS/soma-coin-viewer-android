package com.soma.coinviewer.feature.home

import androidx.lifecycle.viewModelScope
import com.soma.coinviewer.common_ui.base.BaseViewModel
import com.soma.coinviewer.domain.repository.BinanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val binanceRepository: BinanceRepository
) : BaseViewModel() {
    private val _listSortType = MutableStateFlow<ListSortType>(ListSortType.TOTAL_TRADE)
    val listSortType = _listSortType.asStateFlow()

    val coinData = binanceRepository.binanceTickerData
        .onEach { delay(200L) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList(),
        )

    internal fun updateSortType(asc: ListSortType, desc: ListSortType) {
        val currentSortType = _listSortType.value

        _listSortType.value = when (currentSortType) {
            asc -> desc
            desc -> ListSortType.TOTAL_TRADE
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