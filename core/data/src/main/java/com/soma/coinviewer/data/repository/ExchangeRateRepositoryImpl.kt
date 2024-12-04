package com.soma.coinviewer.data.repository

import android.util.Log
import com.soma.coinviewer.data.network.datasource.ExchangeRateDataSource
import com.soma.coinviewer.data.network.util.onResponse
import com.soma.coinviewer.domain.model.ExchangeRate
import com.soma.coinviewer.domain.repository.ExchangeRateRepository
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

class ExchangeRateRepositoryImpl @Inject constructor(
    private val exchangeRateDataSource: ExchangeRateDataSource,
) : ExchangeRateRepository {

    /**
     * [한국수출수입은행-현재환율 API](https://www.koreaexim.go.kr/ir/HPHKIR020M01?apino=2&viewtype=C#tab1)
     *
     * 비영업일의 데이터, 혹은 영업당일 11시 이전에 해당일의 데이터를 요청할 경우 null 값이 반환하기 떄문에
     *
     * 응답 값이 **null**일 경우 하루 전의 데이터를 호출
     */
    override suspend fun getExchangeRate(): List<ExchangeRate> {
        val today = LocalDate.now(ZoneId.of("Asia/Seoul"))
        val result = exchangeRateDataSource.getExchangeRate(today)
            .onResponse()

        if (result.isNotEmpty()) {
            return result.map { it.toVO() }
        }

        return exchangeRateDataSource.getExchangeRate(today.minusDays(1))
            .onResponse()
            .map { it.toVO() }
    }
}