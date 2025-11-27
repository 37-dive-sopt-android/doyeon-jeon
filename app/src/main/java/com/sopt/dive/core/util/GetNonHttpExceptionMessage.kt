package com.sopt.dive.core.util

import com.sopt.dive.R
import kotlinx.coroutines.TimeoutCancellationException

fun getNonHttpExceptionMessage(
    e: Throwable,
): Int = when (e) {
    is TimeoutCancellationException -> R.string.timeout_error_message
    else -> R.string.unknown_error_message
}