package com.sopt.dive.ui.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sopt.dive.R
import com.sopt.dive.data.datastore.DataStoreKeys
import com.sopt.dive.data.datastore.dataStore
import com.sopt.dive.data.model.UserPrefs
import com.sopt.dive.ui.component.ScreenTitle
import kotlinx.coroutines.flow.map

@Composable
fun SplashScreen(
    navigateToHome: () -> Unit,
    navigateToLogin: () -> Unit,
) {
    val context = LocalContext.current

    // 저장된 데이터 불러오기
    val userPrefs by context.dataStore.data
        .map { preferences ->
            UserPrefs(
                isLoggedIn = preferences[DataStoreKeys.IS_LOGGED_IN],
                id = preferences[DataStoreKeys.ID],
                pw = preferences[DataStoreKeys.PW],
                nickname = preferences[DataStoreKeys.NICKNAME],
                mbti = preferences[DataStoreKeys.MBTI]
            )
        }
        .collectAsStateWithLifecycle(null)

    LaunchedEffect(userPrefs) {
        userPrefs?.let {
            when (it.isLoggedIn) {
                true -> navigateToHome()
                else -> navigateToLogin()
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ScreenTitle(stringResource(R.string.sopt))
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    SplashScreen(
        navigateToHome = {},
        navigateToLogin = {}
    )
}