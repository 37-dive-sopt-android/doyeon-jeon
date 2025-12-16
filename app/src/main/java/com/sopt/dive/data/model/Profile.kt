package com.sopt.dive.data.model

data class Profile(
    val image: String = "https://upload3.inven.co.kr/upload/2021/02/21/bbs/i15728541617.jpg",
    val name: String,
    val bio: String? = null,
    val isBirthday: Boolean = false,
    val isUpdated: Boolean = false,
    val music: ProfileMusic? = null
) {
    companion object {
        val myProfile: Profile = Profile(
            name = "도도",
            bio = "도도가 뛰면 도도도도",
        )

        val homeProfiles: List<Profile> = listOf(
            Profile(
                name = "고고",
            ),
            Profile(
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
                name = "로로로",
                isUpdated = true,
                bio = "나 잘거야 말리지마!!!!!!!"
            ),
            Profile(
                name = "모모",
                isBirthday = true,
                bio = "하하이하이하이하이하이하이하이하이하이하이하이하이이하이하이하이하이하이하이하이하이하이하이하이하이하이하이하이하이하이하이하이하이하이하이하이"
            ),
            Profile(
                name = "보보",
                bio = "안녕안녕안녕안녕안녕안녕",
                music = ProfileMusic(
                    id = 1,
                    title = "LINK IT ALL",
                    singer = "SPYAIR"
                )
            ),
            Profile(
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
                name = "보보보보보보보보보보보보보보보보보보보보보보보보보보보보보보보보보",
                bio = "안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕",
                music = ProfileMusic(
                    id = 1,
                    title = "雨上がりに咲く花",
                    singer = "SPYAIR"
                )
            ),
            Profile(
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
                name = "보보보보보보보보보보보보보보보보보보보보보보보보보보보보보보보보보",
                bio = "안녕안녕안녕안녕안녕안녕"
            ),
            Profile(
                name = "보보",
                bio = "안녕안녕안녕안녕안녕안녕"
            ),
            Profile(
                name = "보보",
                bio = "안녕안녕안녕안녕안녕안녕"
            ),
            Profile(
                name = "보보",
                bio = "안녕안녕안녕안녕안녕안녕"
            ),
            Profile(
                name = "보보",
                bio = "안녕안녕안녕안녕안녕안녕"
            ),
        )
    }
}
