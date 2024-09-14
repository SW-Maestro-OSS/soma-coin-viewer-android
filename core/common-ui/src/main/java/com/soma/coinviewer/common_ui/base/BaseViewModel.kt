package com.soma.coinviewer.common_ui.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleException(throwable)
    }

    private fun handleException(throwable: Throwable) {
        // 예외 처리 (예: 로깅, 사용자에게 알림 등)
        Log.e("Exception", "${throwable.message}")
    }

    protected fun launchCoroutine(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(exceptionHandler) {
            block()
        }
    }
}