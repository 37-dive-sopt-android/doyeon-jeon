package com.sopt.dive.presentation.splash

class SplashContract {
    sealed class SplashSideEffect {
        data object NavigateToHome : SplashSideEffect()
        data object NavigateToLogin : SplashSideEffect()
    }
}
