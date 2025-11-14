package com.sopt.dive.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorData(
    @SerialName("code")
    val code: String,
    @SerialName("message")
    val message: String,
)
