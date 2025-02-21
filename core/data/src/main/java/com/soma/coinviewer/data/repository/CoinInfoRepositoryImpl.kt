package com.soma.coinviewer.data.repository

import com.soma.coinviewer.data.network.datasource.RemoteCoinInfoDataSource
import com.soma.coinviewer.domain.model.coin.CoinInfoData
import com.soma.coinviewer.domain.repository.CoinInfoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CoinInfoRepositoryImpl @Inject constructor(
    private val webSocketDataSource: RemoteCoinInfoDataSource,
    private val scope: CoroutineScope,
) : CoinInfoRepository {
    private val _coinInfoData = MutableStateFlow<HashMap<String, CoinInfoData>>(HashMap())
    override val coinInfoData: StateFlow<HashMap<String, CoinInfoData>> =
        _coinInfoData.asStateFlow()

    override fun connect() = webSocketDataSource.connect()
    override fun disconnect() = webSocketDataSource.disconnect()
    override fun subscribeWebSocketData(): Result<Unit> = runCatching {
        scope.launch {
            webSocketDataSource.subscribeWebSocket()
                .collect { response ->
                    val newCoinInfoData = HashMap(_coinInfoData.value)

                    response?.onEach {
                        val data = it.toVO()
                        newCoinInfoData[data.symbol] = data
                    }

                    _coinInfoData.value = newCoinInfoData
                }
        }
    }
}
