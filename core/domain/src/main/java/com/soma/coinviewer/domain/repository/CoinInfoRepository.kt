package com.soma.coinviewer.domain.repository

import com.soma.coinviewer.domain.model.CoinInfoData
import kotlinx.coroutines.flow.StateFlow

interface CoinInfoRepository {
    val sortedCoinInfoData: StateFlow<List<CoinInfoData>>
    val coinInfoData: HashMap<String, CoinInfoData>

    fun connect()
    fun disconnect()
    fun subscribeWebSocketData()
}