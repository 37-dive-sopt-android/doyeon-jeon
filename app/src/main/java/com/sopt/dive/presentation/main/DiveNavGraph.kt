package com.sopt.dive.presentation.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sopt.dive.presentation.home.Home
import com.sopt.dive.presentation.home.HomeRoute
import com.sopt.dive.presentation.login.Login
import com.sopt.dive.presentation.login.LoginRoute
import com.sopt.dive.presentation.profile.Profile
import com.sopt.dive.presentation.profile.ProfileRoute
import com.sopt.dive.presentation.register.Register
import com.sopt.dive.presentation.register.RegisterRoute
import com.sopt.dive.presentation.search.Search
import com.sopt.dive.presentation.search.SearchRoute
import com.sopt.dive.presentation.splash.Splash
import com.sopt.dive.presentation.splash.SplashRoute

fun NavGraphBuilder.diveNavGraph(
    // Screen에서 navigation 필요한 경우,
    // 여기서 navController 활용해 함수 만들어 전달
    navController: NavController,
    innerPadding: PaddingValues,
) {
    composable<Splash> {
        SplashRoute(
            navigateToHome = {
                navController.navigate(Home) {
                    popUpTo(Splash) { inclusive = true }
                }
            },
            navigateToLogin = {
                navController.navigate(Login) {
                    popUpTo(Splash) { inclusive = true }
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
    composable<Login> {
        LoginRoute(
            navigateToHome = {
                navController.navigate(Home) {
                    popUpTo(Login) { inclusive = true }
                }
            },
            navigateToRegister = {
                navController.navigate(Register)
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
    composable<Register> {
        RegisterRoute(
            popToLogin = {
                navController.popBackStack()
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
    composable<Home> {
        HomeRoute(
            modifier = Modifier.padding(innerPadding)
        )
    }
    composable<Search> {
        SearchRoute(
            modifier = Modifier.padding(innerPadding)
        )
    }
    composable<Profile> {
        ProfileRoute(
            navigateToLogin = {
                navController.navigate(Login) {
                    popUpTo(Profile) { inclusive = true }
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}