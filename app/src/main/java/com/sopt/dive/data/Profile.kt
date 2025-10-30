package com.sopt.dive.data

import androidx.annotation.DrawableRes

data class Profile(
    @DrawableRes val image: Int,
    val name: String,
    val bio: String? = null,
    val isBirthday: Boolean = false,
    val isUpdated: Boolean = false,
    val music: ProfileMusic? = null
)