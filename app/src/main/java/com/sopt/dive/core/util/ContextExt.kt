package com.sopt.dive.core.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import com.sopt.dive.presentation.util.errorMessage

fun Context.showToast(
    message: String,
    duration: Int = Toast.LENGTH_SHORT
) {
    Toast.makeText(this, message, duration).show()
}

fun Context.showToast(
    @StringRes messageRes: Int,
    duration: Int = Toast.LENGTH_SHORT
) {
    Toast.makeText(this, getString(messageRes), duration).show()
}

fun Context.showServerErrorToast(
    e: Throwable,
    duration: Int = Toast.LENGTH_SHORT
) {
    Toast.makeText(this, e.errorMessage(this), duration).show()
}