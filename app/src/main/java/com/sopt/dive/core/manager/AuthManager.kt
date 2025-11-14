package com.sopt.dive.core.manager

object AuthManager {
    private var _userId: Int? = null
    val userId get() = _userId

    fun setUserId(value: Int) {
        _userId = value
    }
}
