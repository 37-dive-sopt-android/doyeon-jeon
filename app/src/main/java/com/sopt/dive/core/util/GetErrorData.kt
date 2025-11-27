package com.sopt.dive.core.util

import com.sopt.dive.core.model.BaseResponse
import com.sopt.dive.core.model.ErrorData
import kotlinx.serialization.json.Json
import retrofit2.HttpException

val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

fun getErrorData(e: HttpException): BaseResponse<ErrorData>? =
    try {
        val errorBodyString = e.response()?.errorBody()?.string() ?: return null
        json.decodeFromString<BaseResponse<ErrorData>>(errorBodyString)
    } catch (_: Exception) {
        null
    }
