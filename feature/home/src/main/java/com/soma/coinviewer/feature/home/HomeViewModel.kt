package com.soma.coinviewer.feature.home

import com.soma.coinviewer.common_ui.base.BaseViewModel
import com.soma.coinviewer.domain.repository.BinanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val binanceRepository: BinanceRepository
) : BaseViewModel() {
    private val _listSortType = MutableStateFlow<ListSortType>(ListSortType.TOTAL_TRADE)
    val listSortType = _listSortType.asStateFlow()

    internal fun setListSortType(listSortType: ListSortType) {
        _listSortType.value = listSortType
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