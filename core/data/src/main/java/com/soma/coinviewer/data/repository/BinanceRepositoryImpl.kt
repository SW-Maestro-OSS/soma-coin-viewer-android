package com.soma.coinviewer.data.repository

import com.soma.coinviewer.data.datasource.BinanceDataSource
import com.soma.coinviewer.domain.entity.BinanceTickerData
import com.soma.coinviewer.domain.entity.BinanceTickerData.Companion.BINANCE_TICKER_DATA_MAX_SIZE
import com.soma.coinviewer.domain.repository.BinanceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.math.BigDecimal
import java.util.TreeMap
import javax.inject.Inject

class BinanceRepositoryImpl @Inject constructor(
    private val webSocketDataSource: BinanceDataSource,
) : BinanceRepository {
    private val _binanceTickerData = MutableStateFlow<List<BinanceTickerData>>(emptyList())
    override val binanceTickerData: StateFlow<List<BinanceTickerData>> =
        _binanceTickerData.asStateFlow()

    private val binanceTickerDataMap = TreeMap<Pair<BigDecimal, String>, BinanceTickerData>(
        compareBy<Pair<BigDecimal, String>> { it.first }
            .thenBy { it.second }
    )

    private val isContainData = HashMap<String, BigDecimal>()

    override suspend fun disconnect() = webSocketDataSource.disconnect()
    override suspend fun connect() = webSocketDataSource.connect()
        .collect { response ->
            response.onEach {
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
