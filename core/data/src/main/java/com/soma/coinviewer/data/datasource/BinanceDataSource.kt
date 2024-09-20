package com.soma.coinviewer.data.datasource

import android.util.Log
import com.google.gson.Gson
import com.soma.coinviewer.domain.entity.BinanceMessage
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class BinanceDataSource @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val request: Request
) {

    private var webSocket: WebSocket? = null
    private var listener: WebSocketListener? = null
    private var messageCallback: ((String) -> Unit)? = null

    fun connect() {
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

    fun disconnect() {
        webSocket?.close(1000, "Close Binance")
        webSocket = null
        listener = null
    }

    suspend fun sendMessage(message: BinanceMessage): String? {
        return suspendCoroutine { continuation ->
            messageCallback = { responseMessage ->
                // Responses are returned as JSON in text frames, one response per frame.
                continuation.resume(responseMessage) // onMessage의 text를 반환
            }

            // Requests must be sent as JSON in text frames, one request per frame.
            val jsonMessage = Gson().toJson(message)
            val isSuccess = webSocket?.send(jsonMessage)

            Log.d("send Message Test", isSuccess.toString())

            if (isSuccess == false) {
                continuation.resumeWith(Result.failure(Exception("Failed to send message")))
            }
        }
    }
}