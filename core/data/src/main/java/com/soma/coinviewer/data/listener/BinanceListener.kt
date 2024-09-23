package com.soma.coinviewer.data.listener

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import javax.inject.Inject

class BinanceListener @Inject constructor(
) : WebSocketListener() {
    private val _responseMessage = MutableStateFlow<String?>(null)
    val responseMessage : StateFlow<String?> = _responseMessage.asStateFlow()

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        Log.d("WebSocket onOpen", response.code.toString())
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        Log.d("WebSocket onMessage text", text)

        _responseMessage.value = text
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        super.onMessage(webSocket, bytes)
        Log.d("WebSocket onMessage bytes", bytes.toString())
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        Log.d("WebSocket onClosed", code.toString())
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        Log.d("WebSocket onFailure", response.toString())
    }
}