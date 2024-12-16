package com.soma.coinviewer.data.repository

import com.soma.coinviewer.data.network.datasource.CoinInfoDataSource
import com.soma.coinviewer.domain.model.CoinInfoData
import com.soma.coinviewer.domain.model.CoinInfoData.Companion.COIN_INFO_TICKER_DATA_MAX_SIZE
import com.soma.coinviewer.domain.model.CoinInfoKey
import com.soma.coinviewer.domain.repository.CoinInfoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.TreeMap
import javax.inject.Inject

class CoinInfoRepositoryImpl @Inject constructor(
    private val webSocketDataSource: CoinInfoDataSource,
    private val scope: CoroutineScope,
) : CoinInfoRepository {
    private val _sortedCoinInfoData = MutableStateFlow<List<CoinInfoData>>(emptyList())
    override val sortedCoinInfoData: StateFlow<List<CoinInfoData>> =
        _sortedCoinInfoData.asStateFlow()

    override val coinInfoData = HashMap<String, CoinInfoData>()

    private val coinInfoDataMap = TreeMap<CoinInfoKey, CoinInfoData>()
    private val isContainData = HashMap<String, BigDecimal>()

    override fun connect() = webSocketDataSource.connect()
    override fun disconnect() = webSocketDataSource.disconnect()
    override fun subscribeWebSocketData() {
        scope.launch {
            webSocketDataSource.subscribeWebSocket()
                .collect { response ->
                    response?.onEach {
                        val data = it.toVO()
                        val symbol = data.symbol
                        val currentVolume = data.totalTradedQuoteAssetVolume

                        // 기존 Symbol이 존재하는 경우, TreeMap에서 이전 항목 제거
                        isContainData[symbol]?.let { previousVolume ->
                            val keyToRemove = CoinInfoKey(previousVolume, symbol)
                            coinInfoDataMap.remove(keyToRemove) // 이전 항목 삭제
                        }

                        // 새 데이터를 TreeMap에 추가
                        val newKey = CoinInfoKey(currentVolume, symbol)
                        coinInfoDataMap[newKey] = data
                        isContainData[symbol] = currentVolume

                        // TreeMap 크기 제한 유지
                        while (coinInfoDataMap.size > COIN_INFO_TICKER_DATA_MAX_SIZE) {
                            val lastEntry = coinInfoDataMap.pollLastEntry()
                            lastEntry?.key?.symbol?.let { isContainData.remove(it) }
                        }

                        // CoinDetail 페이지를 위한 HashMap
                        coinInfoData[symbol] = data
                    }

                    _sortedCoinInfoData.value = coinInfoDataMap.map { it.value }
                }
        }
    }
}