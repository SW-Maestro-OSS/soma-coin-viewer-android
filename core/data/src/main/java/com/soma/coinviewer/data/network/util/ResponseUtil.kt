package com.soma.coinviewer.data.network.util

import com.soma.coinviewer.domain.model.exception.HttpResponseException
import com.soma.coinviewer.domain.model.exception.HttpResponseStatus
import retrofit2.Response

internal fun <T> Response<T>.onResponse(): Result<T> = runCatching {
    if (isSuccessful) {
        body() ?: throw HttpResponseException(
            status = HttpResponseStatus.create(-1),
            msg = "응답 본문이 없습니다."
        )
    } else {
        errorBody()?.let {
            throw HttpResponseException(
                status = HttpResponseStatus.create(code()),
                msg = "인터넷 연결을 확인해주세요.",
            )
        } ?: throw HttpResponseException(
            status = HttpResponseStatus.create(-1),
            msg = "에러 본문이 없습니다."
        )
    }
}
