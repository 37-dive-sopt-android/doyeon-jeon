package com.sopt.dive.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.sopt.dive.core.network.ServicePool
import com.sopt.dive.data.datasource.local.DataStoreDataSourceImpl
import com.sopt.dive.data.datasource.local.dataStore
import com.sopt.dive.data.datasource.remote.auth.AuthDataSourceImpl
import com.sopt.dive.data.repository.auth.AuthRepository
import com.sopt.dive.data.repository.auth.AuthRepositoryImpl
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class SplashViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _sideEffect = MutableSharedFlow<SplashSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    init {
        checkLoginStatus()
    }

    fun checkLoginStatus() {
        viewModelScope.launch {
            authRepository.autoLogin()
                .onSuccess {
                    _sideEffect.emit(
                        SplashSideEffect.NavigateToHome
                    )
                }
                .onFailure {
                    _sideEffect.emit(
                        SplashSideEffect.NavigateToLogin
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
                val authDataSource = AuthDataSourceImpl(
                    authService = ServicePool.authService
                )
                val authRepository = AuthRepositoryImpl(
                    authDataSource = authDataSource,
                    dataStoreDataSource = dataStoreDataSource
                )

                return SplashViewModel(authRepository) as T
            }
        }
    }
}
