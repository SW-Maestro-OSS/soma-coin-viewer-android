package com.soma.coinviewer.data.datasource

import android.util.Log
import com.google.gson.Gson
import com.soma.coinviewer.domain.datasource.BinanceDataSource
import com.soma.coinviewer.domain.entity.BinanceOrderBookMessage
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class BinanceDataSourceImpl @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val request: Request
) : BinanceDataSource {

    private var webSocket: WebSocket? = null
    private var listener: WebSocketListener? = null
    private var messageCallback: ((String) -> Unit)? = null

    override fun connect() {
        listener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                Log.d("WebSocket onOpen", response.code.toString())
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)

                Log.d("WebSocket onMessage text", text)

                // 받은 메시지를 콜백으로 전달 --- 테스트를 위한 임시방편입니다!!!
                messageCallback?.invoke(text)
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
        webSocket?.close(1000, "Close Binance")
        webSocket = null
        listener = null
    }

    override suspend fun sendMessage(message: BinanceOrderBookMessage): String? {
        return suspendCoroutine { continuation ->
            messageCallback = { receivedMessage ->
                continuation.resume(receivedMessage) // onMessage의 text를 반환
            }

            val jsonMessage = Gson().toJson(message)
            val isSuccess = webSocket?.send(jsonMessage)

            Log.d("send Message Test", isSuccess.toString())

            if (isSuccess == false) {
                continuation.resumeWith(Result.failure(Exception("Failed to send message")))
            }
        }
    }
}