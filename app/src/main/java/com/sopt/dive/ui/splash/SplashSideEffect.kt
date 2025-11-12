package com.sopt.dive.ui.splash

sealed class SplashSideEffect {
    data object NavigateToHome : SplashSideEffect()
    data object NavigateToLogin : SplashSideEffect()
}
