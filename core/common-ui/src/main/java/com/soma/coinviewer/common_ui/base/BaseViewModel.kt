package com.soma.coinviewer.common_ui.base

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

open class BaseViewModel : ViewModel() {
    protected val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleException(throwable)
    }

    private fun handleException(throwable: Throwable) {
        // 예외 처리 (예: 로깅, 사용자에게 알림 등)
        Log.e("Exception", "${throwable.message}")
    }
}