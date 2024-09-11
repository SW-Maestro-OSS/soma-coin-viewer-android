package com.soma.coinviewer.domain.datasource

import com.soma.coinviewer.domain.entity.BinanceOrderBookMessage

interface BinanceDataSource {
    fun connect()
    fun disconnect()
    suspend fun sendMessage(message: BinanceOrderBookMessage): String?
}