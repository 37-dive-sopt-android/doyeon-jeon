package com.sopt.dive.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sopt.dive.ui.home.Home
import com.sopt.dive.ui.home.HomeRoute
import com.sopt.dive.ui.login.Login
import com.sopt.dive.ui.login.LoginScreen
import com.sopt.dive.ui.profile.Profile
import com.sopt.dive.ui.profile.ProfileRoute
import com.sopt.dive.ui.register.Register
import com.sopt.dive.ui.register.RegisterScreen
import com.sopt.dive.ui.search.Search
import com.sopt.dive.ui.search.SearchRoute
import com.sopt.dive.ui.splash.Splash
import com.sopt.dive.ui.splash.SplashScreen

fun NavGraphBuilder.diveNavGraph(
    // Screen에서 navigation 필요한 경우,
    // 여기서 navController 활용해 함수 만들어 전달
    navController: NavController,
    innerPadding: PaddingValues,
) {
    composable<Splash> {
        SplashScreen(
            navigateToHome = {
                navController.navigate(Home) {
                    popUpTo(Splash) { inclusive = true }
                }
            },
            navitageToLogin = {
                navController.navigate(Login) {
                    popUpTo(Splash) { inclusive = true }
                }
            }
        )
    }
    composable<Login> {
        LoginScreen(
            navigateToHome = {
                navController.navigate(Home) {
                    popUpTo(Splash) { inclusive = true }
                }
            },
            navigateToRegister = {
                navController.navigate(Register)
            }
        )
    }
    composable<Register> {
        RegisterScreen(
            popToLogin = {
                navController.popBackStack()
            }
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
            modifier = Modifier.padding(innerPadding)
        )
    }
}