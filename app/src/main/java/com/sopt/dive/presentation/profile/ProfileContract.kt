package com.sopt.dive.presentation.profile

import com.sopt.dive.data.model.User

class ProfileContract {
    data class ProfileUiState(
        val myInfo: User = User(
            id = "",
            nickname = "",
            email = "",
            age = 0,
            status = ""
        ),
    )

    sealed class ProfileSideEffect {
        data object NavigateToLogin : ProfileSideEffect()
        data class ShowErrorToast(val e: Throwable) : ProfileSideEffect()
    }
}
