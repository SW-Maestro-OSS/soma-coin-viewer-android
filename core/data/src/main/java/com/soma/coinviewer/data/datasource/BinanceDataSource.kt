package com.soma.coinviewer.data.datasource

import com.soma.coinviewer.data.listener.BinanceListener
import com.soma.coinviewer.data.model.BinanceTickerResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import javax.inject.Inject

class BinanceDataSource @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val request: Request,
    private val binanceListener: BinanceListener,
) {
    private var webSocket: WebSocket? = null

    fun connect(): Flow<Array<BinanceTickerResponse>> {
        webSocket = okHttpClient.newWebSocket(request, binanceListener)
        return binanceListener.responseMessage
    }

    fun disconnect() {
        webSocket?.close(1000, "Close Binance")
        webSocket = null
    }
}