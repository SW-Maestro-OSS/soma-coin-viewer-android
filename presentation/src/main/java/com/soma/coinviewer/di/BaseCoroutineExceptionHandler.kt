package com.soma.coinviewer.di

import com.soma.coinviewer.domain.model.exception.ErrorHelper
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class BaseCoroutineExceptionHandler @Inject constructor(
    private val errorHelper: ErrorHelper,
) : CoroutineExceptionHandler {
    override val key: CoroutineContext.Key<*>
        get() = CoroutineExceptionHandler

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        errorHelper.sendError(exception)
    }
}