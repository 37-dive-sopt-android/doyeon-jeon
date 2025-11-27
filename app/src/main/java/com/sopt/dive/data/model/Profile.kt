package com.sopt.dive.data.model

data class Profile(
    val image: String = "https://upload3.inven.co.kr/upload/2021/02/21/bbs/i15728541617.jpg",
    val name: String,
    val bio: String? = null,
    val isBirthday: Boolean = false,
    val isUpdated: Boolean = false,
    val music: ProfileMusic? = null
)