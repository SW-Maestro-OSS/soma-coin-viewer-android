package com.soma.coinviewer.domain.repository

import com.soma.coinviewer.domain.entity.BinanceMessage
import kotlinx.coroutines.flow.StateFlow

interface BinanceRepository {
    fun connect()
    fun disconnect()
    fun sendMessage(message: BinanceMessage): Boolean
    fun subscribeWebSocketData(): StateFlow<String?>
}