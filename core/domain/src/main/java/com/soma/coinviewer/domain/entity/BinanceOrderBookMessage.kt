package com.soma.coinviewer.domain.entity

data class BinanceOrderBookMessage(
    val id: String,
    val method: String,
    val params: BinanceOrderBookParams,
)

data class BinanceOrderBookParams(
    val symbol: String,
    val limit: Int?,
)
