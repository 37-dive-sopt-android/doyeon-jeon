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

    sealed class RegisterEvent {
        data class OnIdChanged(val value: String) : RegisterEvent()
        data class OnPwChanged(val value: String) : RegisterEvent()
        data class OnNicknameChanged(val value: String) : RegisterEvent()
        data class OnEmailChanged(val value: String) : RegisterEvent()
        data class OnAgeChanged(val value: String) : RegisterEvent()
        data object OnRegisterBtnClicked : RegisterEvent()
    }

    sealed class RegisterSideEffect {
        data object PopToLogin : RegisterSideEffect()
        data class ShowToast(val message: Int) : RegisterSideEffect()
        data class ShowErrorToast(val e: Throwable) : RegisterSideEffect()
    }
}
