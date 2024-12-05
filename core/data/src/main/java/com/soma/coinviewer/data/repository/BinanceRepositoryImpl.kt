package com.soma.coinviewer.data.repository

import com.soma.coinviewer.data.network.datasource.BinanceDataSource
import com.soma.coinviewer.domain.model.BinanceTickerData
import com.soma.coinviewer.domain.model.BinanceTickerData.Companion.BINANCE_TICKER_DATA_MAX_SIZE
import com.soma.coinviewer.domain.repository.BinanceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.TreeMap
import javax.inject.Inject

class BinanceRepositoryImpl @Inject constructor(
    private val webSocketDataSource: BinanceDataSource,
    private val scope: CoroutineScope,
) : BinanceRepository {
    private val _binanceTickerData = MutableStateFlow<List<BinanceTickerData>>(emptyList())
    override val binanceTickerData: StateFlow<List<BinanceTickerData>> =
        _binanceTickerData.asStateFlow()

    private val binanceTickerDataMap = TreeMap<Pair<BigDecimal, String>, BinanceTickerData>(
        compareBy<Pair<BigDecimal, String>> { it.first }
            .thenBy { it.second }
    )

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
                            binanceTickerDataMap.remove(previousVolume to symbol) // 이전 항목 삭제
                        }

                        binanceTickerDataMap[currentVolume to symbol] = data
                        isContainData[symbol] = currentVolume

                        while (binanceTickerDataMap.size > BINANCE_TICKER_DATA_MAX_SIZE) {
                            val lastEntry = binanceTickerDataMap.pollLastEntry()
                            isContainData.remove(lastEntry?.key?.second)
                        }
                    }

                    _binanceTickerData.value = binanceTickerDataMap.map { it.value }
                }
        }
    }
}
