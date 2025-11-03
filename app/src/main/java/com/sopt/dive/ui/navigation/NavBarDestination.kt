package com.sopt.dive.ui.navigation

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.sopt.dive.R
import com.sopt.dive.ui.home.Home
import com.sopt.dive.ui.profile.Profile
import com.sopt.dive.ui.search.Search

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
        route = Search
    ),
    ProfileDestination(
        icon = R.drawable.ic_profile,
        label = "프로필",
        route = Profile
    )
}