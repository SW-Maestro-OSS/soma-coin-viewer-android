package com.soma.coinviewer.domain.repository

import com.soma.coinviewer.domain.model.CoinInfoData
import kotlinx.coroutines.flow.StateFlow

interface CoinInfoRepository {
    val coinInfoData: StateFlow<List<CoinInfoData>>

    fun connect()
    fun disconnect()
    fun subscribeWebSocketData()
}