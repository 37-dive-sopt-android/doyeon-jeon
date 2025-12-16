package com.sopt.dive.presentation.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sopt.dive.R
import com.sopt.dive.presentation.component.ScreenTitle

@Composable
fun SplashRoute(
    navigateToHome: () -> Unit,
    navigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = viewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect {
            when (it) {
                is SplashSideEffect.NavigateToHome -> navigateToHome()
                is SplashSideEffect.NavigateToLogin -> navigateToLogin()
            }
        }
    }

    SplashScreen(
        modifier = modifier
    )
}

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ScreenTitle(stringResource(R.string.sopt))
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    SplashScreen()
}
