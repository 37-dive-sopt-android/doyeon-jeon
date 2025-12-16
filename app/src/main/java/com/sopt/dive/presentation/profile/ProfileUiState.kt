package com.sopt.dive.presentation.profile

import com.sopt.dive.data.model.User

data class ProfileUiState (
    val myInfo: User = User(
        id = "",
        nickname = "",
        email = "",
        age = 0,
        status = ""
    )
)
