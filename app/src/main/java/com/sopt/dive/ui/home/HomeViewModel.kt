package com.sopt.dive.ui.home

import androidx.lifecycle.ViewModel
import com.sopt.dive.data.mock.homeProfiles
import com.sopt.dive.data.mock.myProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        getMyProfile()
        getOtherProfiles()
    }

    fun getMyProfile() {
        _uiState.value = _uiState.value.copy(
            myProfile = myProfile
        )
    }

    fun getOtherProfiles() {
        _uiState.value = _uiState.value.copy(
            otherProfiles = homeProfiles
        )
    }
}