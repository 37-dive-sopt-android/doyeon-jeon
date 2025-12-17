package com.sopt.dive.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.dive.R
import com.sopt.dive.data.repository.AuthRepository
import com.sopt.dive.di.feature.AuthModule
import com.sopt.dive.presentation.login.LoginSideEffect.NavigateToHome
import com.sopt.dive.presentation.login.LoginSideEffect.ShowErrorToast
import com.sopt.dive.presentation.login.LoginSideEffect.ShowToast
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel() : ViewModel() {
    private val authRepository: AuthRepository = AuthModule.authRepository

    private var _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<LoginSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun onInputIdChanged(value: String) {
        _uiState.update {
            it.copy(
                inputId = value
            )
        }
    }

    fun onInputPwChanged(value: String) {
        _uiState.update {
            it.copy(
                inputPw = value
            )
        }
    }

    fun onLoginClick() {
        if (!_uiState.value.isLoginButtonEnabled) return

        viewModelScope.launch {
            authRepository.login(
                id = _uiState.value.inputId,
                password = _uiState.value.inputPw
            ).onSuccess {
                // 홈으로 이동
                _sideEffect.emit(
                    NavigateToHome
                )
                // 로그인 완료 토스트
                _sideEffect.emit(
                    ShowToast(
                        R.string.login_success_message
                    )
                )
            }.onFailure { e ->
                _sideEffect.emit(ShowErrorToast(e))
            }
        }
    }
}
