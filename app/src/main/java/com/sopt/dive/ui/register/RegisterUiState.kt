package com.sopt.dive.ui.register

data class RegisterUiState(
    val id: String = "",
    val pw: String = "",
    val nickname: String = "",
    val email: String = "",
    val age: String = "",
) {
    val isRegisterButtonEnabled: Boolean
        get() =
            id.isNotBlank() && pw.isNotBlank() && nickname.isNotBlank() && email.isNotBlank() && age.isNotBlank()
}
