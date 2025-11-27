package com.sopt.dive.ui.profile

sealed class ProfileSideEffect {
    data object NavigateToLogin : ProfileSideEffect()
    data class ShowToast(val message: Int) : ProfileSideEffect()
}