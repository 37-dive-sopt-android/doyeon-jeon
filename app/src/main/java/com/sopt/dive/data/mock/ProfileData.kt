package com.sopt.dive.data.mock

import com.sopt.dive.R
import com.sopt.dive.data.model.Profile
import com.sopt.dive.data.model.ProfileMusic

val myProfile: Profile = Profile(
    image = R.drawable.chopper,
    name = "도도",
    bio = "도도가 뛰면 도도도도",
)

val homeProfiles: List<Profile> = listOf(
    Profile(
        image = R.drawable.chopper,
        name = "고고",
    ),
    Profile(
        image = R.drawable.chopper,
        name = "노노노노노노노노노노노노노노노노노노노노노노노노노노",
        bio = "잘거야잘거야잘거야!!!",
        isBirthday = true,
        isUpdated = true,
        music = ProfileMusic(
            id = 1,
            title = "BE WITH",
            singer = "SPYAIR"
        )
    ),
    Profile(
        image = R.drawable.chopper,
        name = "로로로",
        isUpdated = true,
        bio = "나 잘거야 말리지마!!!!!!!"
    ),
    Profile(
        image = R.drawable.chopper,
        name = "모모",
        isBirthday = true,
        bio = "하하이하이하이하이하이하이하이하이하이하이하이하이이하이하이하이하이하이하이하이하이하이하이하이하이하이하이하이하이하이하이하이하이하이하이하이"
    ),
    Profile(
        image = R.drawable.chopper,
        name = "보보",
        bio = "안녕안녕안녕안녕안녕안녕",
        music = ProfileMusic(
            id = 1,
            title = "LINK IT ALL",
            singer = "SPYAIR"
        )
    ),
    Profile(
        image = R.drawable.chopper,
        name = "보보",
        bio = "안녕안녕안녕안녕안녕안녕",
        isUpdated = true,
        music = ProfileMusic(
            id = 1,
            title = "雨上がりに咲く花",
            singer = "SPYAIR"
        )
    ),
    Profile(
        image = R.drawable.chopper,
        name = "보보보보보보보보보보보보보보보보보보보보보보보보보보보보보보보보보",
        bio = "안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕",
        music = ProfileMusic(
            id = 1,
            title = "雨上がりに咲く花",
            singer = "SPYAIR"
        )
    ),
    Profile(
        image = R.drawable.chopper,
        name = "보보보보보보보보보보보보보보보보보보보보보보보보보보보보보보보보보",
        bio = "안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕",
        isUpdated = true,
        music = ProfileMusic(
            id = 1,
            title = "雨上がりに咲く花",
            singer = "SPYAIR"
        )
    ),
    Profile(
        image = R.drawable.chopper,
        name = "보보보보보보보보보보보보보보보보보보보보보보보보보보보보보보보보보",
        bio = "안녕안녕안녕안녕안녕안녕"
    ),
    Profile(
        image = R.drawable.chopper,
        name = "보보",
        bio = "안녕안녕안녕안녕안녕안녕"
    ),
    Profile(
        image = R.drawable.chopper,
        name = "보보",
        bio = "안녕안녕안녕안녕안녕안녕"
    ),
    Profile(
        image = R.drawable.chopper,
        name = "보보",
        bio = "안녕안녕안녕안녕안녕안녕"
    ),
    Profile(
        image = R.drawable.chopper,
        name = "보보",
        bio = "안녕안녕안녕안녕안녕안녕"
    ),
)