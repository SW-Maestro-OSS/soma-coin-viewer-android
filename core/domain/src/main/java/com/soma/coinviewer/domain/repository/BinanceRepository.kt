package com.soma.coinviewer.domain.repository

import com.soma.coinviewer.domain.model.BinanceTickerData
import kotlinx.coroutines.flow.StateFlow

interface BinanceRepository {
    val binanceTickerData: StateFlow<List<BinanceTickerData>>

    fun connect()
    fun disconnect()
    fun subscribeWebSocketData()
}