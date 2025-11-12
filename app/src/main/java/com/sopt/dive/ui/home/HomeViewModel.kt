package com.sopt.dive.ui.home

import androidx.lifecycle.ViewModel
import com.sopt.dive.data.mock.homeProfiles
import com.sopt.dive.data.mock.myProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        getMyProfile()
        getOtherProfiles()
    }

    fun getMyProfile() {
        _uiState.update {
            it.copy(
                myProfile = myProfile
            )
        }
    }

    fun getOtherProfiles() {
        _uiState.update {
            it.copy(
                otherProfiles = homeProfiles
            )
        }
    }
}