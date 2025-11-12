package com.sopt.dive.ui.register

sealed class RegisterSideEffect {
    data object PopToLogin : RegisterSideEffect()
    data class ShowToast(val message: Int) : RegisterSideEffect()
}
