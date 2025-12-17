package com.sopt.dive.presentation.register

sealed class RegisterSideEffect {
    data object PopToLogin : RegisterSideEffect()
    data class ShowToast(val message: Int) : RegisterSideEffect()

    data class ShowErrorToast(val e: Throwable) : RegisterSideEffect()
}
