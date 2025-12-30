package com.sopt.dive.presentation.register

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.dive.R
import com.sopt.dive.data.repository.UserRepository
import com.sopt.dive.di.feature.UserModule
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.sopt.dive.presentation.register.RegisterContract.*
import com.sopt.dive.presentation.register.RegisterContract.RegisterSideEffect.*
import com.sopt.dive.presentation.register.RegisterContract.RegisterEvent.*

class RegisterViewModel() : ViewModel() {
    private val userRepository: UserRepository = UserModule.userRepository

    private var _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<RegisterSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    private val idRegex = "^[A-Za-z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]{6,10}$".toRegex()
    private val pwRegex =
        "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?])[A-Za-z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]{8,12}$".toRegex()
    private val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()

    fun setEvent(event: RegisterEvent) {
        handleEvent(event)
    }

    private fun handleEvent(event: RegisterEvent) {
        when (event) {
            is OnAgeChanged -> onAgeChanged(event.value)
            is OnIdChanged -> onIdChanged(event.value)
            is OnNicknameChanged -> onNicknameChanged(event.value)
            is OnEmailChanged -> onEmailChanged(event.value)
            is OnPwChanged -> onPwChanged(event.value)
            OnRegisterBtnClicked -> onRegisterClick()
        }
    }

    fun onIdChanged(value: String) {
        _uiState.update {
            it.copy(
                id = value
            )
        }
    }

    fun onPwChanged(value: String) {
        _uiState.update {
            it.copy(
                pw = value
            )
        }
    }

    fun onNicknameChanged(value: String) {
        _uiState.update {
            it.copy(
                nickname = value
            )
        }
    }

    fun onEmailChanged(value: String) {
        _uiState.update {
            it.copy(
                email = value
            )
        }
    }

    fun onAgeChanged(value: String) {
        if (value.isDigitsOnly()) {
            _uiState.update {
                it.copy(
                    age = value
                )
            }
        }
    }

    fun isIdValid(): Boolean =
        _uiState.value.id.matches(idRegex)

    fun isPwValid(): Boolean =
        _uiState.value.pw.matches(pwRegex)

    fun isEmailValid(): Boolean =
        _uiState.value.email.matches(emailRegex)

    fun isAgeValid(): Boolean =
        _uiState.value.age.isDigitsOnly()

    fun onRegisterClick() {
        // 버튼 비활성 상태
        if (!_uiState.value.isRegisterButtonEnabled) return

        viewModelScope.launch {
            getRegisterValidErrorEffect()?.let { effect ->
                _sideEffect.emit(effect)
                return@launch
            }

            userRepository.signUp(
                id = _uiState.value.id,
                password = _uiState.value.pw,
                name = _uiState.value.nickname,
                email = _uiState.value.email,
                age = _uiState.value.age.toInt()
            ).onSuccess {
                // 로그인 페이지로 돌아가기
                _sideEffect.emit(
                    PopToLogin
                )
                // 회원가입 완료 토스트
                _sideEffect.emit(
                    ShowToast(
                        R.string.register_success_message
                    )
                )
            }.onFailure { e ->
                _sideEffect.emit(ShowErrorToast(e))
            }
        }
    }

    private fun getRegisterValidErrorEffect(
    ): RegisterSideEffect? = when {
        !isIdValid() -> ShowToast(
            R.string.register_id_fail_message
        )

        !isPwValid() -> ShowToast(
            R.string.register_pw_fail_message
        )

        !isEmailValid() -> ShowToast(
            R.string.register_email_fail_message
        )

        !isAgeValid() -> ShowToast(
            R.string.register_age_fail_message
        )

        else -> null
    }
}
