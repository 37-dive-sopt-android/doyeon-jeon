package com.sopt.dive.ui.register

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.sopt.dive.R
import com.sopt.dive.core.network.ServicePool
import com.sopt.dive.data.datasource.local.DataStoreDataSourceImpl
import com.sopt.dive.data.datasource.local.dataStore
import com.sopt.dive.data.datasource.remote.user.UserDataSourceImpl
import com.sopt.dive.data.repository.user.UserRepository
import com.sopt.dive.data.repository.user.UserRepositoryImpl
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class RegisterViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {
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
            // 아이디가 유효하지 않은 경우
            if (!isIdValid()) {
                _sideEffect.emit(
                    RegisterSideEffect.ShowToast(
                        R.string.register_id_fail_message
                    )
                )
                return@launch
            }
            // 비밀번호가 유효하지 않은 경우
            if (!isPwValid()) {
                _sideEffect.emit(
                    RegisterSideEffect.ShowToast(
                        R.string.register_pw_fail_message
                    )
                )
                return@launch
            }
            // 이메일이 유효하지 않은 경우
            if (!isEmailValid()) {
                _sideEffect.emit(
                    RegisterSideEffect.ShowToast(
                        R.string.register_email_fail_message
                    )
                )
                return@launch
            }
            // 나이가 유효하지 않은 경우
            if (!isAgeValid()) {
                _sideEffect.emit(
                    RegisterSideEffect.ShowToast(
                        R.string.register_age_fail_message
                    )
                )
                return@launch
            }
            // 위 케이스 모두 통과했다면 > 회원가입
            userRepository.signUp(
                id = _uiState.value.id,
                password = _uiState.value.pw,
                name = _uiState.value.nickname,
                email = _uiState.value.email,
                age = _uiState.value.age.toInt()
            ).onSuccess {
                // 로그인 페이지로 돌아가기
                _sideEffect.emit(
                    RegisterSideEffect.PopToLogin
                )
                // 회원가입 완료 토스트
                _sideEffect.emit(
                    RegisterSideEffect.ShowToast(
                        R.string.register_success_message
                    )
                )
            }.onFailure {
                _sideEffect.emit(
                    RegisterSideEffect.ShowToast(
                        R.string.register_fail_message
                    )
                )
            }
        }
    }

    // 수동 DI
    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras,
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                val dataStoreDataSource = DataStoreDataSourceImpl(
                    application.dataStore
                )
                val userDataSource = UserDataSourceImpl(
                    userService = ServicePool.userService
                )
                val userRepository = UserRepositoryImpl(
                    userDataSource = userDataSource,
                    dataStoreDataSource = dataStoreDataSource,
                )
                return RegisterViewModel(userRepository) as T
            }
        }
    }
}
