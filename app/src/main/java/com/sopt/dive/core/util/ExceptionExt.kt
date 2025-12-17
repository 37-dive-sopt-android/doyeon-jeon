package com.sopt.dive.core.util

import com.sopt.dive.core.model.BaseResponse
import com.sopt.dive.core.model.ErrorData
import kotlinx.serialization.json.Json
import retrofit2.HttpException

val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

fun HttpException.serverError(): BaseResponse<ErrorData>? =
    try {
        val errorBodyString = this.response()?.errorBody()?.string() ?: return null
        json.decodeFromString<BaseResponse<ErrorData>>(errorBodyString)
    } catch (_: Exception) {
        null
    }
