package com.sopt.dive.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.sopt.dive.R
import com.sopt.dive.core.exception.UnauthorizedException
import com.sopt.dive.core.network.ServicePool
import com.sopt.dive.core.util.getNonHttpExceptionMessage
import com.sopt.dive.data.datasource.local.DataStoreDataSourceImpl
import com.sopt.dive.data.datasource.local.dataStore
import com.sopt.dive.data.datasource.remote.user.UserDataSourceImpl
import com.sopt.dive.data.repository.user.UserRepository
import com.sopt.dive.data.repository.user.UserRepositoryImpl
import com.sopt.dive.ui.profile.ProfileSideEffect.ShowToast
import com.sopt.dive.ui.profile.ProfileSideEffect.NavigateToLogin
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException

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
                    dataStoreDataSource = dataStoreDataSource,
                    userDataSource = userDataSource,
                )
                return ProfileViewModel(userRepository) as T
            }
        }
    }
}
