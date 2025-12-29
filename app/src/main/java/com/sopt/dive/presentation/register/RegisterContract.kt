package com.sopt.dive.presentation.register

class RegisterContract {
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

    sealed class RegisterSideEffect {
        data object PopToLogin : RegisterSideEffect()
        data class ShowToast(val message: Int) : RegisterSideEffect()

        data class ShowErrorToast(val e: Throwable) : RegisterSideEffect()
    }
}
