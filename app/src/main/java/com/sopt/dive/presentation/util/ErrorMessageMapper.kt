package com.sopt.dive.presentation.util

import android.content.Context
import com.sopt.dive.R
import com.sopt.dive.data.type.AuthError
import com.sopt.dive.data.type.CommonError

fun Throwable.errorMessage(
    context: Context,
): String = when (this) {
    is CommonError.Timeout -> context.getString(R.string.timeout_error_message)
    is CommonError.Undefined -> serverMessage
    is AuthError.IdDuplicated -> context.getString(R.string.register_duplicate_id_error_message)
    is AuthError.InvalidCredentials -> context.getString(R.string.login_invalid_fail_message)
    is AuthError.TokenExpired -> context.getString(R.string.common_need_login)
    else -> context.getString(R.string.unknown_error_message)
}
