package com.sopt.dive.presentation.login

class LoginContract{
    data class LoginUiState(
        val inputId: String = "",
        val inputPw: String = "",
    ) {
        val isLoginButtonEnabled: Boolean
            get() =
                inputId.isNotBlank() && inputPw.isNotBlank()
    }

    sealed class LoginSideEffect {
        data object NavigateToHome : LoginSideEffect()
        data class ShowToast(val message: Int) : LoginSideEffect()
        data class ShowErrorToast(val e: Throwable) : LoginSideEffect()
    }
}
