package com.sopt.dive.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.sopt.dive.data.datasource.local.DataStoreDataSource
import com.sopt.dive.data.datasource.local.DataStoreDataSourceImpl
import com.sopt.dive.data.datasource.local.dataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class ProfileViewModel(
    private val dataStoreDataSource: DataStoreDataSource,
) : ViewModel() {
    private var _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getUserPrefs()
    }

    fun getUserPrefs() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    userPrefs = dataStoreDataSource.getUserInfo()
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

                return ProfileViewModel(dataStoreDataSource) as T
            }
        }
    }
}
