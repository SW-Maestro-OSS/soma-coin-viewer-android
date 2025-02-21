package com.soma.coinviewer.data.network.model

import com.google.gson.annotations.SerializedName
import com.soma.coinviewer.domain.model.exchangerate.ExchangeRate
import java.math.BigDecimal

/**
 * @property result 조회 결과:
 *                  1: 성공,
 *                  2: DATA 코드 오류,
 *                  3: 인증 코드 오류,
 *                  4: 일일 제한 초과.
 * @property currencyCode 통화 코드 (예: "USD").
 * @property currencyName 국가 및 통화 이름 (예: "미국 달러").
 * @property receiveRateInWon 전신환(송금) 받을 때의 환율.
 * @property sendRateToForeignCurrency 전신환(송금) 보낼 때의 환율.
 * @property baseExchangeRate 매매 기준율.
 * @property bookValueRate 장부 가격.
 * @property yearlyExchangeFeeRate 연간 환가료율.
 * @property tenDayExchangeFeeRate 10일 환가료율.
 * @property seoulFxBaseRate 서울외국환중개에서 제공하는 매매 기준율.
 * @property seoulFxBookValueRate 서울외국환중개에서 제공하는 장부 가격.
 */

data class ExchangeRateResponse(
    @SerializedName("result") val result: Int?,
    @SerializedName("cur_unit") val currencyCode: String?,
    @SerializedName("cur_nm") val currencyName: String?,
    @SerializedName("ttb") val receiveRateInWon: String?,
    @SerializedName("tts") val sendRateToForeignCurrency: String?,
    @SerializedName("deal_bar_r") val baseExchangeRate: String?,
    @SerializedName("bkpr") val bookValueRate: String?,
    @SerializedName("yy_efee_r") val yearlyExchangeFeeRate: String?,
    @SerializedName("ten_dd_efee_r") val tenDayExchangeFeeRate: String?,
    @SerializedName("kftc_deal_bas_r") val seoulFxBaseRate: String?,
    @SerializedName("kftc_bkpr") val seoulFxBookValueRate: String?,
) {
    fun toVO() = ExchangeRate(
        currencyCode = currencyCode ?: "UNKNOWN",
        receiveRateInWon = receiveRateInWon?.let { BigDecimal(it.replace(",", "")) }
            ?: BigDecimal.ZERO,
    )
}
