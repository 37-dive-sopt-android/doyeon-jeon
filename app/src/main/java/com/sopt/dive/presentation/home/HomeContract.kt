package com.sopt.dive.presentation.home

import com.sopt.dive.data.model.Profile

class HomeContract {
    data class HomeUiState (
        val myProfile: Profile? = null,
        val otherProfiles: List<Profile> = listOf()
    )
}
