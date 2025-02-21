package com.soma.coinviewer.domain.model.exception

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorHelper @Inject constructor() {
    private val _errorEvent = Channel<Throwable>(BUFFERED)
    val errorFlow = _errorEvent.receiveAsFlow()

    fun sendError(error: Throwable) {
        _errorEvent.trySend(error)
    }
}
