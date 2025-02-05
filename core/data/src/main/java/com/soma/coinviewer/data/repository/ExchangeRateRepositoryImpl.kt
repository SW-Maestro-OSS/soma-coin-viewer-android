package com.soma.coinviewer.data.repository

import com.soma.coinviewer.data.datastore.datasource.LocalExchangeRateDataSource
import com.soma.coinviewer.data.network.datasource.RemoteExchangeRateDataSource
import com.soma.coinviewer.domain.repository.ExchangeRateRepository
import kotlinx.coroutines.flow.first
import java.math.BigDecimal
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

class ExchangeRateRepositoryImpl @Inject constructor(
    private val remoteExchangeRateDataSource: RemoteExchangeRateDataSource,
    private val localExchangeRateDataSource: LocalExchangeRateDataSource,
) : ExchangeRateRepository {

    /**
     * [한국수출수입은행-현재환율 API](https://www.koreaexim.go.kr/ir/HPHKIR020M01?apino=2&viewtype=C#tab1)
     *
     * 비영업일의 데이터, 혹은 영업당일 11시 이전에 해당일의 데이터를 요청할 경우 null 값이 반환하기 떄문에
     *
     * 응답 값이 **null**일 경우 하루 전의 데이터를 호출
     */
    override suspend fun updateExchangeRate() {
        val today = LocalDate.now(ZoneId.of(SEOUL_TIME_ZONE))
        val result = remoteExchangeRateDataSource.getExchangeRate(today)

        if (result.isNotEmpty()) {
            result.map { it.toVO() }
                .onEach { localExchangeRateDataSource.setExchangeRate(it) }
            return
        }

        remoteExchangeRateDataSource.getExchangeRate(today.minusDays(1))
            .map { it.toVO() }
            .onEach { localExchangeRateDataSource.setExchangeRate(it) }
    }

    override suspend fun getExchangeRate(currencyCode: String): BigDecimal {
        return localExchangeRateDataSource.getExchangeRate(currencyCode).first()
    }

    companion object {
        private const val SEOUL_TIME_ZONE = "Asia/Seoul"
    }
}
