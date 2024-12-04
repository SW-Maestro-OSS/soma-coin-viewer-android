package com.soma.coinviewer.data.di

import com.soma.coinviewer.data.repository.BinanceRepositoryImpl
import com.soma.coinviewer.data.repository.ExchangeRateRepositoryImpl
import com.soma.coinviewer.domain.repository.BinanceRepository
import com.soma.coinviewer.domain.repository.ExchangeRateRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsWebSocketRepository(
        webSocketRepositoryImpl: BinanceRepositoryImpl
    ): BinanceRepository

    @Binds
    @Singleton
    abstract fun bindsExchangeRateRepository(
        exchangeRateRepositoryImpl: ExchangeRateRepositoryImpl
    ): ExchangeRateRepository
}
