package com.soma.coinviewer.domain.repository

import com.soma.coinviewer.domain.entity.BinanceOrderBookMessage

interface BinanceRepository {
    fun connect()
    fun disconnect()
    fun sendMessage(message: BinanceOrderBookMessage)
}