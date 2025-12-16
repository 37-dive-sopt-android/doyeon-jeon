package com.sopt.dive.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.dive.data.model.Profile
import com.sopt.dive.di.feature.ReqresModule
import com.sopt.dive.data.repository.ReqresRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val reqresRepository: ReqresRepository = ReqresModule.reqresRepository

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        getMyProfile()
        getOtherProfiles()
    }

    fun getMyProfile() {
        _uiState.update {
            it.copy(
                myProfile = Profile.myProfile
            )
        }
    }

    fun getOtherProfiles() {
        val homeProfiles = Profile.homeProfiles

        viewModelScope.launch {
            reqresRepository.getUserList()
                .onSuccess { images ->
                    val profileWithImages = homeProfiles.mapIndexed { index, profile ->
                        profile.copy(
                            image = images.getOrNull(index) ?: profile.image
                        )
                    }
                    _uiState.update { currentState ->
                        currentState.copy(
                            otherProfiles = profileWithImages
                        )
                    }
                }
        }
    }

}