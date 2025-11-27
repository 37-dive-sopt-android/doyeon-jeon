package com.sopt.dive.ui.login

data class LoginUiState(
    val inputId: String = "",
    val inputPw: String = "",
) {
    val isLoginButtonEnabled: Boolean
        get() =
            inputId.isNotBlank() && inputPw.isNotBlank()
}
