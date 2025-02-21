package com.soma.coinviewer.data.network.datasource

import com.soma.coinviewer.data.network.listener.BinanceListener
import com.soma.coinviewer.data.network.model.BinanceTickerResponse
import kotlinx.coroutines.flow.StateFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import javax.inject.Inject

class RemoteCoinInfoDataSource @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val request: Request,
    private val binanceListener: BinanceListener,
) {
    private var webSocket: WebSocket? = null

    fun connect() = runCatching {
        webSocket = okHttpClient.newWebSocket(request, binanceListener)
    }

    fun disconnect() = runCatching {
        webSocket?.close(1000, "Close Binance")
        webSocket = null
    }

    fun subscribeWebSocket(): StateFlow<Array<BinanceTickerResponse>?> =
        binanceListener.responseMessage
}
