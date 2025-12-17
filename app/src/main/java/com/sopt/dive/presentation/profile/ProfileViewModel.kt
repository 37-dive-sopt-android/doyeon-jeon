package com.sopt.dive.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.dive.data.repository.UserRepository
import com.sopt.dive.data.type.AuthError
import com.sopt.dive.di.feature.UserModule
import com.sopt.dive.presentation.profile.ProfileSideEffect.NavigateToLogin
import com.sopt.dive.presentation.profile.ProfileSideEffect.ShowErrorToast
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel() : ViewModel() {
    private val userRepository: UserRepository = UserModule.userRepository

    private var _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<ProfileSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun getMyInfo() {
        viewModelScope.launch {
            userRepository.getMyInfo()
                .onSuccess { result ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            myInfo = result
                        )
                    }
                }
                .onFailure { e ->
                    _sideEffect.emit(ShowErrorToast(e))
                    if (e is AuthError.TokenExpired) {
                        _sideEffect.emit(NavigateToLogin)
                    }
                }
        }
    }
}
