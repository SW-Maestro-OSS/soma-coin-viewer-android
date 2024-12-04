package com.soma.coinviewer.data.model

data class ExchangeRateResponse(
    val result: Int,
    val cur_unit: String,
    val ttb: String,
    val tts: String,
    val deal_bas_r: String,
    val bkpr: String,
    val yy_efee_r: String,
    val ten_dd_efee_r: String,
    val kftc_bkpr: String,
    val kftc_deal_bas_r: String,
    val cur_nm: String
)