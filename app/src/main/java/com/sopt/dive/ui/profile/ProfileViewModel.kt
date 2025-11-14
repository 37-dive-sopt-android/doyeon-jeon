package com.sopt.dive.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.sopt.dive.R
import com.sopt.dive.core.network.ServicePool
import com.sopt.dive.data.datasource.remote.user.UserDataSourceImpl
import com.sopt.dive.data.repository.user.UserRepository
import com.sopt.dive.data.repository.user.UserRepositoryImpl
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class ProfileViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {
    private var _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<ProfileSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun getMyInfo() {
        viewModelScope.launch {
            userRepository.getMyInfo()
                .onSuccess { result ->
                    _uiState.update { currentState ->
                        _uiState.value.copy(
                            myInfo = result
                        )
                    }
                }
                .onFailure { e ->
                    when (e) {
                        is TimeoutCancellationException -> _sideEffect.emit(
                            ProfileSideEffect.ShowToast(
                                message = R.string.timeout_error_message
                            )
                        )

                        else -> _sideEffect.emit(
                            ProfileSideEffect.ShowToast(
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
                val userDataSource = UserDataSourceImpl(
                    userService = ServicePool.userService
                )
                val userRepository = UserRepositoryImpl(
                    userDataSource = userDataSource
                )
                return ProfileViewModel(userRepository) as T
            }
        }
    }
}
