package com.soma.coinviewer.data.datasource

import com.soma.coinviewer.domain.datasource.WebSocketDataSource
import okhttp3.OkHttpClient
import okhttp3.WebSocket
import javax.inject.Inject

class WebSocketDataSourceImpl @Inject constructor(
    private val client: OkHttpClient
) : WebSocketDataSource {

    private var webSocket: WebSocket? = null

}