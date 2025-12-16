package com.sopt.dive.presentation.profile

sealed class ProfileSideEffect {
    data object NavigateToLogin : ProfileSideEffect()
    data class ShowToast(val message: Int) : ProfileSideEffect()
}