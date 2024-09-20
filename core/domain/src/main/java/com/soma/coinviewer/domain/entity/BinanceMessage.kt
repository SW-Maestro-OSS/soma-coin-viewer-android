package com.soma.coinviewer.domain.entity

data class BinanceMessage(
    val id: String,      // INT / STRING / null -> ⚠️ 저희가 사용할 걸로 보이는 API들에서는 다 String인 걸로 보입니다
    val method: String,
    val params: BinanceParams?,
)