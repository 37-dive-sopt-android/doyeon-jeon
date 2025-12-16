package com.sopt.dive.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.dive.di.feature.AuthModule
import com.sopt.dive.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SplashViewModel() : ViewModel() {
    private val authRepository: AuthRepository = AuthModule.authRepository

    private val _sideEffect = MutableSharedFlow<SplashSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    init {
        checkLoginStatus()
    }

    fun checkLoginStatus() {
        viewModelScope.launch {
            authRepository.autoLogin()
                .onSuccess {
                    _sideEffect.emit(
                        SplashSideEffect.NavigateToHome
                    )
                }
                .onFailure {
                    _sideEffect.emit(
                        SplashSideEffect.NavigateToLogin
                    )
                }
        }
    }
}
