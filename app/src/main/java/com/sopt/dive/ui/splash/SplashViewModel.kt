package com.sopt.dive.ui.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.sopt.dive.data.datasource.local.DataStoreDataSource
import com.sopt.dive.data.datasource.local.DataStoreDataSourceImpl
import com.sopt.dive.data.datasource.local.dataStore
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class SplashViewModel(
    private val dataStoreDataSource: DataStoreDataSource,
) : ViewModel() {
    private val _sideEffect = MutableSharedFlow<SplashSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    init {
        checkLoginStatus()
    }

    fun checkLoginStatus() {
        viewModelScope.launch {
            Log.d("DIVE", "userInfo 가져오기 시작")
            val userInfo = dataStoreDataSource.getUserInfo()
            Log.d("DIVE", "userInfo 가져오기 끝")

            if (userInfo != null && userInfo.isLoggedIn == true && userInfo.id != null && userInfo.pw != null && userInfo.nickname != null && userInfo.mbti != null) {
                Log.d("DIVE", "홈 가야 함")

                _sideEffect.emit(
                    SplashSideEffect.NavigateToHome
                )
            } else {
                Log.d("DIVE", "로그인 가야 함")

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

                return SplashViewModel(dataStoreDataSource) as T
            }
        }
    }
}
