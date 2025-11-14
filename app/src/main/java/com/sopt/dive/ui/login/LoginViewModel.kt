package com.sopt.dive.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.sopt.dive.R
import com.sopt.dive.core.network.ServicePool
import com.sopt.dive.core.util.getErrorData
import com.sopt.dive.data.datasource.local.DataStoreDataSourceImpl
import com.sopt.dive.data.datasource.local.dataStore
import com.sopt.dive.data.datasource.remote.auth.AuthDataSourceImpl
import com.sopt.dive.data.repository.auth.AuthRepository
import com.sopt.dive.data.repository.auth.AuthRepositoryImpl
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException

@Suppress("UNCHECKED_CAST")
class LoginViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {
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
                    LoginSideEffect.NavigateToHome
                )
                // 로그인 완료 토스트
                _sideEffect.emit(
                    LoginSideEffect.ShowToast(
                        R.string.login_success_message
                    )
                )
            }.onFailure { e ->
                when (e) {
                    is HttpException -> {
                        val errorData = getErrorData(e)
                        when {
                            errorData == null -> _sideEffect.emit(
                                LoginSideEffect.ShowToast(
                                    message = R.string.unknown_error_message
                                )
                            )

                            errorData.data.code == "COMMON-400-VAL" -> _sideEffect.emit(
                                LoginSideEffect.ShowToast(
                                    message = R.string.login_invalid_fail_message
                                )
                            )

                            errorData.data.code == "COMMON-401" -> _sideEffect.emit(
                                LoginSideEffect.ShowToast(
                                    message = R.string.login_invalid_fail_message
                                )
                            )

                            else -> _sideEffect.emit(
                                LoginSideEffect.ShowStringToast(
                                    message = errorData.data.message
                                )
                            )
                        }
                    }

                    is TimeoutCancellationException -> _sideEffect.emit(
                        LoginSideEffect.ShowToast(
                            message = R.string.timeout_error_message
                        )
                    )

                    else -> _sideEffect.emit(
                        LoginSideEffect.ShowToast(
                            message = R.string.unknown_error_message
                        )
                    )
                }
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
                val authDataSource = AuthDataSourceImpl(
                    authService = ServicePool.authService
                )
                val authRepository = AuthRepositoryImpl(
                    authDataSource = authDataSource,
                    dataStoreDataSource = dataStoreDataSource
                )

                return LoginViewModel(authRepository) as T
            }
        }
    }
}
