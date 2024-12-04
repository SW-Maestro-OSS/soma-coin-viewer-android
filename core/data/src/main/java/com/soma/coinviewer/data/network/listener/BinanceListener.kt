package com.soma.coinviewer.data.network.listener

import com.google.gson.Gson
import com.soma.coinviewer.data.network.model.BinanceTickerResponse
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject

class BinanceListener @Inject constructor() : WebSocketListener() {
    private val gson = Gson()

    private val _responseMessage = Channel<Array<BinanceTickerResponse>>(Channel.BUFFERED)
    val responseMessage: Flow<Array<BinanceTickerResponse>> = _responseMessage.receiveAsFlow()

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)

        val response = gson.fromJson(text, Array<BinanceTickerResponse>::class.java)
        _responseMessage.trySend(response)
    }
}