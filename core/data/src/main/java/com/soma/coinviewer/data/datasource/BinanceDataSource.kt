package com.soma.coinviewer.data.datasource

import com.google.gson.Gson
import com.soma.coinviewer.data.listener.BinanceListener
import com.soma.coinviewer.domain.entity.BinanceMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class BinanceDataSource @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val request: Request,
    private val binanceListener: BinanceListener,
) {

    private var webSocket: WebSocket? = null

    fun connect() {
        webSocket = okHttpClient.newWebSocket(request, binanceListener)
    }

    fun disconnect() {
        webSocket?.close(1000, "Close Binance")
        webSocket = null
    }

    fun sendMessage(message: BinanceMessage): Boolean {
        val jsonMessage = Gson().toJson(message)
        val isSuccess = webSocket?.send(jsonMessage)

        return isSuccess ?: false
    }

    fun subscribeWebSocket() : StateFlow<String?> {
        return binanceListener.responseMessage
    }
}