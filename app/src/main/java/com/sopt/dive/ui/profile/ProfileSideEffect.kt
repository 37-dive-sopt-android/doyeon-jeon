package com.sopt.dive.ui.profile

sealed class ProfileSideEffect {
    data class ShowToast(val message: Int) : ProfileSideEffect()
}