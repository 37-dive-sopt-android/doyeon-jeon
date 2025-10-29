package com.sopt.dive

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sopt.dive.ui.home.Home
import com.sopt.dive.ui.home.HomeScreen
import com.sopt.dive.ui.profile.Profile
import com.sopt.dive.ui.profile.ProfileScreen
import com.sopt.dive.ui.search.Search
import com.sopt.dive.ui.search.SearchScreen

fun NavGraphBuilder.diveNavGraph(
    // Screen에서 navigation 필요한 경우,
    // 여기서 navController 활용해 함수 만들어 전달
    navController: NavController
) {
    composable<Home> {
        HomeScreen()
    }
    composable<Search> {
        SearchScreen()
    }
    composable<Profile> {
        // 임시로 하드코딩
        // 추후 ProfileScreen에서 DataStore 로드 & 파라미터 전달 안 하도록 개선
        ProfileScreen("t", "e", "m", "p")
    }
}