package com.soma.coinviewer.domain.repository

import com.soma.coinviewer.domain.model.coin.CoinInfoData
import kotlinx.coroutines.flow.StateFlow

interface CoinInfoRepository {
    val coinInfoData: StateFlow<HashMap<String, CoinInfoData>>

    fun connect()
    fun disconnect()
    fun subscribeWebSocketData()
}
