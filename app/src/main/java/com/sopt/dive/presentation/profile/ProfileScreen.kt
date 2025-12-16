package com.sopt.dive.presentation.profile

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sopt.dive.R
import com.sopt.dive.data.mock.ProfileData
import com.sopt.dive.data.model.UserModel
import com.sopt.dive.presentation.component.InfoBox
import com.sopt.dive.presentation.component.ProfileCard

@Composable
fun ProfileRoute(
    navigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = viewModel(),
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getMyInfo()

        viewModel.sideEffect.collect {
            when (it) {
                is ProfileSideEffect.ShowToast -> Toast.makeText(
                    context,
                    context.getString(it.message),
                    Toast.LENGTH_SHORT
                ).show()

                is ProfileSideEffect.NavigateToLogin -> navigateToLogin.invoke()
            }
        }
    }

    ProfileScreen(
        myInfo = uiState.myInfo,
        modifier = modifier
    )
}

@Composable
fun ProfileScreen(
    myInfo: UserModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(
            vertical = 24.dp,
            horizontal = 20.dp
        ),
    ) {
        ProfileCard(
            profile = ProfileData.myProfile
        )
        Spacer(Modifier.height(40.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            InfoBox(
                label = stringResource(R.string.profile_id_label),
                value = myInfo.id,
            )
            InfoBox(
                label = stringResource(R.string.profile_nickname_label),
                value = myInfo.nickname,
            )
            InfoBox(
                label = stringResource(R.string.profile_email_label),
                value = myInfo.email
            )
            InfoBox(
                label = stringResource(R.string.profile_age_label),
                value = "${myInfo.age}세",
            )
        }
    }
}
