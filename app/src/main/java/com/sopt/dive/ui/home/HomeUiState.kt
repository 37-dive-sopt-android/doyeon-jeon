package com.sopt.dive.ui.home

import com.sopt.dive.data.model.Profile

data class HomeUiState (
    val myProfile: Profile? = null,
    val otherProfiles: List<Profile> = listOf()
)