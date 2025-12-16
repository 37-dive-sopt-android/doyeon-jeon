package com.sopt.dive.presentation.login

sealed class LoginSideEffect {
    data object NavigateToHome : LoginSideEffect()
    data class ShowToast(val message: Int) : LoginSideEffect()
    data class ShowStringToast(val message: String) : LoginSideEffect()
}
