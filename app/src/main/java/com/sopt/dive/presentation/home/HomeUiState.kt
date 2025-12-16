package com.sopt.dive.presentation.home

import com.sopt.dive.data.mock.model.Profile

data class HomeUiState (
    val myProfile: Profile? = null,
    val otherProfiles: List<Profile> = listOf()
)