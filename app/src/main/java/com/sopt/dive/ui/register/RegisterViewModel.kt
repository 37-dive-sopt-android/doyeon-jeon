package com.sopt.dive.ui.register

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
class RegisterViewModel(
    private val dataStoreDataSource: DataStoreDataSource,
) : ViewModel() {
    private var _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<RegisterSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

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

    fun onMbtiChanged(value: String) {
        _uiState.update {
            it.copy(
                mbti = value
            )
        }
    }

    fun onRegisterClick() {
        viewModelScope.launch {
            // 아이디가 유효하지 않은 경우
            if (_uiState.value.id.length !in 6..10) {
                _sideEffect.emit(
                    RegisterSideEffect.ShowToast(
                        R.string.register_id_fail_message
                    )
                )
                return@launch
            }
            // 비밀번호가 유효하지 않은 경우
            if (_uiState.value.pw.length !in 8..12) {
                _sideEffect.emit(
                    RegisterSideEffect.ShowToast(
                        R.string.register_pw_fail_message
                    )
                )
            }
            // 닉네임이 유효하지 않은 경우
            if (_uiState.value.nickname.isBlank()) {
                _sideEffect.emit(
                    RegisterSideEffect.ShowToast(
                        R.string.register_nickname_fail_message
                    )
                )
                return@launch
            }
            // MBTI가 유효하지 않은 경우
            if (_uiState.value.mbti.length != 4) {
                _sideEffect.emit(
                    RegisterSideEffect.ShowToast(
                        R.string.register_mbti_fail_message
                    )
                )
                return@launch
            }
            // 위 케이스 모두 통과했다면 > 회원가입
            dataStoreDataSource.setUserInfo(
                id = _uiState.value.id,
                pw = _uiState.value.pw,
                nickname = _uiState.value.nickname,
                mbti = _uiState.value.mbti
            )
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

                return RegisterViewModel(dataStoreDataSource) as T
            }
        }
    }
}
