package com.sopt.dive.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sopt.dive.R
import com.sopt.dive.ui.component.InfoBox
import com.sopt.dive.ui.component.ProfileBox
import com.sopt.dive.ui.theme.Background

@Composable
fun ProfileScreen(
    id: String,
    pw: String,
    nickname: String,
    mbti: String,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Column(
            modifier = Modifier.padding(
                vertical = 24.dp,
                horizontal = 20.dp
            )
        ) {
            ProfileBox(
                image = R.drawable.chopper,
                name = stringResource(R.string.main_user_name),
                bio = stringResource(R.string.main_user_bio),
            )
            Spacer(Modifier.height(40.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(28.dp)
            ) {
                InfoBox(
                    label = stringResource(R.string.main_id_label),
                    value = id,
                )
                InfoBox(
                    label = stringResource(R.string.main_pw_label),
                    value = pw,
                )
                InfoBox(
                    label = stringResource(R.string.main_nickname_label),
                    value = nickname,
                )
                InfoBox(
                    label = stringResource(R.string.main_mbti_label),
                    value = mbti,
                )
            }
        }
    }
}