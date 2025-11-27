package com.sopt.dive.ui.profile

import com.sopt.dive.data.repository.user.model.UserModel

data class ProfileUiState (
    val myInfo: UserModel = UserModel(
        id = "",
        nickname = "",
        email = "",
        age = 0,
        status = ""
    )
)
