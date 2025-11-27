package com.sopt.dive.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.dive.R
import com.sopt.dive.core.exception.UnauthorizedException
import com.sopt.dive.core.network.ServicePool
import com.sopt.dive.core.util.getNonHttpExceptionMessage
import com.sopt.dive.data.repository.user.UserRepository
import com.sopt.dive.ui.profile.ProfileSideEffect.NavigateToLogin
import com.sopt.dive.ui.profile.ProfileSideEffect.ShowToast
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ProfileViewModel() : ViewModel() {
    private val userRepository: UserRepository = ServicePool.userRepository

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
                    val errorEffect = when (e) {
                        is UnauthorizedException -> NavigateToLogin
                        is HttpException -> ShowToast(R.string.unknown_error_message)
                        else -> ShowToast(getNonHttpExceptionMessage(e))
                    }
                    _sideEffect.emit(errorEffect)
                }
        }
    }
}
