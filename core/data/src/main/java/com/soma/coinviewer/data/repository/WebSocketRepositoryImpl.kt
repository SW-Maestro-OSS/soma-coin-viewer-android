package com.soma.coinviewer.data.repository

import com.soma.coinviewer.domain.datasource.WebSocketDataSource
import com.soma.coinviewer.domain.repository.WebSocketRepository
import javax.inject.Inject

class WebSocketRepositoryImpl @Inject constructor(
    private val webSocketDataSource: WebSocketDataSource
) : WebSocketRepository {
}