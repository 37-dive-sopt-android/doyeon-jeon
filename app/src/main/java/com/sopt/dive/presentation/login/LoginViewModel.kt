package com.sopt.dive.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.dive.R
import com.sopt.dive.di.ServicePool
import com.sopt.dive.core.util.getNonHttpExceptionMessage
import com.sopt.dive.core.util.getServerError
import com.sopt.dive.domain.repository.AuthRepository
import com.sopt.dive.presentation.login.LoginSideEffect.NavigateToHome
import com.sopt.dive.presentation.login.LoginSideEffect.ShowStringToast
import com.sopt.dive.presentation.login.LoginSideEffect.ShowToast
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException


@Suppress("UNCHECKED_CAST")
class LoginViewModel() : ViewModel() {
    private val authRepository: AuthRepository = ServicePool.authRepository

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
                val errorEffect = when (e) {
                    is HttpException -> getLoginHttpExceptionEffect(e)
                    else -> ShowToast(getNonHttpExceptionMessage(e))
                }
                _sideEffect.emit(errorEffect)
            }
        }
    }

    private fun getLoginHttpExceptionEffect(
        e: HttpException,
    ): LoginSideEffect = when (val errorData = getServerError(e)) {
        null -> ShowToast(R.string.unknown_error_message)
        else -> when (errorData.code) {
            "COMMON-400-VAL", "COMMON-401" -> ShowToast(R.string.login_invalid_fail_message)
            else -> ShowStringToast(errorData.message)
        }
    }
}
