package com.soma.coinviewer.domain.repository

import com.soma.coinviewer.domain.entity.BinanceTickerData
import kotlinx.coroutines.flow.StateFlow

interface BinanceRepository {
    val binanceTickerData: StateFlow<List<BinanceTickerData>>

    suspend fun connect()
    suspend fun disconnect()
}