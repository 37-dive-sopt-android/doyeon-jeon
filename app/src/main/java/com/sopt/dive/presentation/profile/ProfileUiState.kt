package com.sopt.dive.presentation.profile

import com.sopt.dive.domain.model.UserModel

data class ProfileUiState (
    val myInfo: UserModel = UserModel(
        id = "",
        nickname = "",
        email = "",
        age = 0,
        status = ""
    )
)
