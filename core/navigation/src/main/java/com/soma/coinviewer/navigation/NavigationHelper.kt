package com.soma.coinviewer.navigation

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationHelper @Inject constructor() {
    private val _navigationFlow = Channel<NavigationTarget>(BUFFERED)
    val navigationFlow = _navigationFlow.receiveAsFlow()

    fun navigateTo(navigationTarget: NavigationTarget) {
        _navigationFlow.trySend(navigationTarget)
    }
}

data class NavigationTarget(val destination: DeepLinkRoute, val popUpTo: Int? = null)