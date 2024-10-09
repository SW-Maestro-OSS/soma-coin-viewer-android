package com.soma.coinviewer.data.di

import com.soma.coinviewer.data.repository.BinanceRepositoryImpl
import com.soma.coinviewer.domain.repository.BinanceRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsWebSocketRepository(
        webSocketRepositoryImpl: BinanceRepositoryImpl
    ): BinanceRepository
}

@Module
@InstallIn(SingletonComponent::class)
object CoroutineModule {

    @Provides
    @Singleton
    fun provideCoroutineScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }
    // Okhttp3를 이용하여 웹소켓 데이터를 받을 때에는 백그라운드 스레드에서 데이터를 받고,
    // 현재 해당 코루틴 스코프를 사용하는 로직은 모두 역직렬화와 같은 후처리이기 때문에 Default Dispatcher를 사용하였습니다.
}