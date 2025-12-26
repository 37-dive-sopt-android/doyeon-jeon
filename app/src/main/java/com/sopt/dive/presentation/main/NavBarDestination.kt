package com.sopt.dive.presentation.main

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.sopt.dive.R
import com.sopt.dive.presentation.home.Home
import com.sopt.dive.presentation.profile.Profile
import com.sopt.dive.presentation.search.Search

enum class NavBarDestination(
    @DrawableRes val icon: Int,
    val label: String,
    val route: Route,
    val selectedColor: Color? = null,
    val unselectedColor: Color? = null,
) {
    HomeDestination(
        icon = R.drawable.ic_home,
        label = "홈",
        route = Home
    ),
    SearchDestination(
        icon = R.drawable.ic_search,
        label = "검색",
        route = Search(id = 1)
    ),
    ProfileDestination(
        icon = R.drawable.ic_profile,
        label = "프로필",
        route = Profile
    )
}