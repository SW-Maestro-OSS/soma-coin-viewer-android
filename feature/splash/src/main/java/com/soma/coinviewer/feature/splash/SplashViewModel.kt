package com.soma.coinviewer.feature.splash

import androidx.lifecycle.viewModelScope
import com.soma.coinviewer.common_ui.base.BaseViewModel
import com.soma.coinviewer.domain.repository.BinanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val binanceRepository: BinanceRepository
) : BaseViewModel() {
    private val _eventFlow = MutableSharedFlow<SplashEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            binanceRepository.connect()
            val subscribeJob = launch { binanceRepository.subscribeWebSocketData() }
            delay(2000L) // 최소 2초는 기다립니다.
            subscribeJob.cancelAndJoin() // 2초가 되어도 연결이 안되었을경우 이를 기다립니다.
            event(SplashEvent.TimerDone)
        }

        // Todo : WebSocket Connect에 Boolean 값 반환 후 성공했을 경우 Navigate 되고, 실패할 경우 추가 조치하도록 변경
    }

    private fun event(event: SplashEvent) = viewModelScope.launch {
        _eventFlow.emit(event)
    }
}

sealed class SplashEvent() {
    data object TimerDone : SplashEvent()
}