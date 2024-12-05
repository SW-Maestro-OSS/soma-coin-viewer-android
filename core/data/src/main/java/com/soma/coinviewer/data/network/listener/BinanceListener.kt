package com.soma.coinviewer.data.network.listener

import com.google.gson.Gson
import com.soma.coinviewer.data.network.model.BinanceTickerResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject

class BinanceListener @Inject constructor() : WebSocketListener() {
    private val gson = Gson()

    private val _responseMessage = MutableStateFlow<Array<BinanceTickerResponse>?>(null)
    val responseMessage: StateFlow<Array<BinanceTickerResponse>?> = _responseMessage.asStateFlow()

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)

        val response = gson.fromJson(text, Array<BinanceTickerResponse>::class.java)
        _responseMessage.value = response
    }
}