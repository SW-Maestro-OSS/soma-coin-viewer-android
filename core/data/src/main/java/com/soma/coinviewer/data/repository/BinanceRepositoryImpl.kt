package com.soma.coinviewer.data.repository

import com.soma.coinviewer.data.datasource.BinanceDataSource
import com.soma.coinviewer.domain.entity.BinanceOrderBookMessage
import com.soma.coinviewer.domain.repository.BinanceRepository
import javax.inject.Inject

class BinanceRepositoryImpl @Inject constructor(
    private val webSocketDataSource: BinanceDataSource
) : BinanceRepository {

    override fun connect() = webSocketDataSource.connect()

    override fun disconnect() = webSocketDataSource.disconnect()

    override suspend fun sendMessage(message: BinanceOrderBookMessage): String? {
        return webSocketDataSource.sendMessage(message)
    }

}