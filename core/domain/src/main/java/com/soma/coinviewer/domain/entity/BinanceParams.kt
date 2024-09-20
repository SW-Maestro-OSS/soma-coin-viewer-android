package com.soma.coinviewer.domain.entity

interface BinanceParams {
    // TODO: symbol, limit도 없는 API가 있어서 비워뒀는데 사용할 API 결정되면 겹치는 걸 여기 넣어도 될 것 같습니다
}

data class OrderBookParams (
    val symbol: String,
    val limit: Int?,    // Default 100; max 5000
) : BinanceParams

data class RecentTradesParams (
    val symbol: String,
    val limit: Int?,    // Default 500; max 1000
) : BinanceParams

data class HistoricalTradesParams (
    val symbol: String,
    val fromId: Int?,   // Trade ID to begin at
    val limit: Int?,    // Default 500; max 1000
) : BinanceParams
