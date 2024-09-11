package com.soma.coinviewer.data.datasource

import android.util.Log
import com.google.gson.Gson
import com.soma.coinviewer.domain.datasource.BinanceDataSource
import com.soma.coinviewer.domain.entity.BinanceOrderBookMessage
import com.soma.coinviewer.domain.entity.BinanceOrderBookParams
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import javax.inject.Inject

class BinanceDataSourceImpl @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val request: Request
) : BinanceDataSource {

    private var webSocket: WebSocket? = null
    private var listener: WebSocketListener? = null

    override fun connect() {
        listener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                Log.d("WebSocket onOpen", response.code.toString())
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)

                Log.d("WebSocket onMessage text", text)
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
        webSocket = okHttpClient.newWebSocket(request, listener as WebSocketListener)
    }

    override fun disconnect() {
        webSocket?.cancel()
        webSocket = null
        listener = null
    }

    override fun sendMessage(message: BinanceOrderBookMessage) {
        Log.d("send Message Test", webSocket?.send(
            Gson()
                .toJson(message)
                .toString()
        ).toString())
    }
}