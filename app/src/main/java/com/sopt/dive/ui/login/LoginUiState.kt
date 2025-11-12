package com.sopt.dive.ui.login

import com.sopt.dive.data.model.AccountInfo

data class LoginUiState(
    val inputId: String = "",
    val inputPw: String = "",
    val accountInfo: AccountInfo? = null,
) {
    val isLoginButtonEnabled: Boolean
        get() =
            accountInfo != null && inputId.isNotBlank() && inputPw.isNotBlank()
}
