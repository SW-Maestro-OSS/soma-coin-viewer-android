package com.soma.coinviewer.navigation

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationHelper @Inject constructor() {
    private val _navigationFlow = Channel<NavigationEvent>(BUFFERED)
    val navigationFlow = _navigationFlow.receiveAsFlow()

    fun navigate(navigationTarget: NavigationEvent) {
        _navigationFlow.trySend(navigationTarget)
    }
}

sealed class NavigationEvent {
    data class To(val destination: DeepLinkRoute, val popUpTo: Int? = null) :
        NavigationEvent()

    data object Up : NavigationEvent()
}
