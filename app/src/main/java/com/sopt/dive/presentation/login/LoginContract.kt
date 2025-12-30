package com.sopt.dive.presentation.login

class LoginContract {
    data class State(
        val inputId: String = "",
        val inputPw: String = "",
    ) {
        val isLoginButtonEnabled: Boolean
            get() =
                inputId.isNotBlank() && inputPw.isNotBlank()
    }

    sealed class Event {
        data class OnIdChanged(val vaule: String) : Event()
        data class OnPwChanged(val value: String) : Event()
        data object OnLoginBtnClicked : Event()
        data object OnRegisterBtnClicked: Event()
    }

    sealed class SideEffect {
        data object NavigateToHome : SideEffect()
        data object NavigateToRegister: SideEffect()
        data class ShowToast(val message: Int) : SideEffect()
        data class ShowErrorToast(val e: Throwable) : SideEffect()
    }
}
