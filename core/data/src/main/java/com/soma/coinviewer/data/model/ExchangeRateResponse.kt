package com.soma.coinviewer.data.model

import com.google.gson.annotations.SerializedName

/**
 * ExchangeRateResponse는 특정 통화의 환율 데이터를 나타냅니다.
 *
 * @property result 조회 결과:
 *                  1: 성공,
 *                  2: DATA 코드 오류,
 *                  3: 인증 코드 오류,
 *                  4: 일일 제한 초과.
 * @property currencyCode 통화 코드 (예: "USD").
 * @property currencyName 국가 및 통화 이름 (예: "미국 달러").
 * @property remittanceReceiveRate 전신환(송금) 받을 때의 환율.
 * @property remittanceSendRate 전신환(송금) 보낼 때의 환율.
 * @property baseExchangeRate 매매 기준율.
 * @property bookValueRate 장부 가격.
 * @property yearlyExchangeFeeRate 연간 환가료율.
 * @property tenDayExchangeFeeRate 10일 환가료율.
 * @property seoulFxBaseRate 서울외국환중개에서 제공하는 매매 기준율.
 * @property seoulFxBookValueRate 서울외국환중개에서 제공하는 장부 가격.
 */

data class ExchangeRateResponse(
    @SerializedName("RESULT") val result: Int,
    @SerializedName("CUR_UNIT") val currencyCode: String,
    @SerializedName("CUR_NM") val currencyName: String,
    @SerializedName("TTB") val remittanceReceiveRate: String,
    @SerializedName("TTS") val remittanceSendRate: String,
    @SerializedName("DEAL_BAS_R") val baseExchangeRate: String,
    @SerializedName("BKPR") val bookValueRate: String,
    @SerializedName("YY_EFEE_R") val yearlyExchangeFeeRate: String,
    @SerializedName("TEN_DD_EFEE_R") val tenDayExchangeFeeRate: String,
    @SerializedName("KFTC_DEAL_BAS_R") val seoulFxBaseRate: String,
    @SerializedName("KFTC_BKPR") val seoulFxBookValueRate: String,
)
