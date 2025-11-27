package com.sopt.dive.ui.register

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.dive.R
import com.sopt.dive.core.network.ServicePool
import com.sopt.dive.core.util.getNonHttpExceptionMessage
import com.sopt.dive.core.util.getServerError
import com.sopt.dive.data.repository.user.UserRepository
import com.sopt.dive.ui.register.RegisterSideEffect.PopToLogin
import com.sopt.dive.ui.register.RegisterSideEffect.ShowStringToast
import com.sopt.dive.ui.register.RegisterSideEffect.ShowToast
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegisterViewModel() : ViewModel() {
    private val userRepository: UserRepository = ServicePool.userRepository

    private var _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<RegisterSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    private val idRegex = "^[A-Za-z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]{6,10}$".toRegex()
    private val pwRegex =
        "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?])[A-Za-z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]{8,12}$".toRegex()
    private val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()

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
                val errorEffect = when (e) {
                    is HttpException -> getRegisterHttpExceptionEffet(e)
                    else -> ShowToast(getNonHttpExceptionMessage(e))
                }
                _sideEffect.emit(errorEffect)
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

    private fun getRegisterHttpExceptionEffet(
        e: HttpException,
    ): RegisterSideEffect = when (val errorData = getServerError(e)) {
        null -> ShowToast(R.string.unknown_error_message)
        else -> when (errorData.code) {
            "COMMON-409" -> ShowToast(R.string.register_duplicate_id_error_message)
            else -> ShowStringToast(errorData.message)
        }
    }
}
