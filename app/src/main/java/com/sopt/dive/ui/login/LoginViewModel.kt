package com.sopt.dive.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.sopt.dive.R
import com.sopt.dive.data.datasource.local.DataStoreDataSource
import com.sopt.dive.data.datasource.local.DataStoreDataSourceImpl
import com.sopt.dive.data.datasource.local.dataStore
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class LoginViewModel(
    private val dataStoreDataSource: DataStoreDataSource,
) : ViewModel() {
    private var _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<LoginSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun getAccountInfo() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    accountInfo = dataStoreDataSource.getAccountInfo()
                )
            }
        }
    }

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
        viewModelScope.launch{
            val currentAccountInfo = _uiState.value.accountInfo

            when {
                // 로딩 중
                currentAccountInfo == null -> Unit
                // 저장된 id/pw가 없는 경우
                currentAccountInfo.id == null || currentAccountInfo.pw == null -> {
                    _sideEffect.emit(
                        LoginSideEffect.ShowToast(
                            R.string.login_need_register_fail_message
                        )
                    )
                }
                // id 또는 pw 미입력한 경우
                _uiState.value.inputId.isBlank() || _uiState.value.inputPw.isBlank() -> {
                    _sideEffect.emit(
                        LoginSideEffect.ShowToast(
                            R.string.login_need_id_and_pw
                        )
                    )
                }
                // id나 pw가 일치하지 않는 경우
                _uiState.value.inputId != currentAccountInfo.id || _uiState.value.inputPw != currentAccountInfo.pw -> {
                    _sideEffect.emit(
                        LoginSideEffect.ShowToast(
                            R.string.login_invalid_fail_message
                        )
                    )
                }
                // 위 케이스 모두 통과했다면 > 로그인
                else -> {
                    dataStoreDataSource.setLoginStatus(true)
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
                }
            }
        }
    }

    // 수동 DI
    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                val dataStoreDataSource = DataStoreDataSourceImpl(
                    application.dataStore
                )

                return LoginViewModel(dataStoreDataSource) as T
            }
        }
    }
}
