package com.sopt.dive.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sopt.dive.R
import com.sopt.dive.data.datastore.DataStoreKeys
import com.sopt.dive.data.model.UserPrefs
import com.sopt.dive.data.datastore.dataStore
import com.sopt.dive.data.mock.myProfile
import com.sopt.dive.ui.component.InfoBox
import com.sopt.dive.ui.component.ProfileCard
import kotlinx.coroutines.flow.map

@Composable
fun ProfileRoute(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    // 저장된 데이터 불러오기
    val userPrefs by context.dataStore.data
        .map { preferences ->
            UserPrefs(
                isLoggedIn = true,
                id = preferences[DataStoreKeys.ID],
                pw = preferences[DataStoreKeys.PW],
                nickname = preferences[DataStoreKeys.NICKNAME],
                mbti = preferences[DataStoreKeys.MBTI]
            )
        }
        .collectAsStateWithLifecycle(null)

    ProfileScreen(
        userPrefs = userPrefs,
        modifier = modifier
    )
}

@Composable
fun ProfileScreen(
    userPrefs: UserPrefs?,
    modifier: Modifier = Modifier,
) {

    userPrefs?.let {
        Column(
            modifier = modifier.padding(
                vertical = 24.dp,
                horizontal = 20.dp
            ),
        ) {
            ProfileCard(
                profile = myProfile
            )
            Spacer(Modifier.height(40.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(28.dp)
            ) {
                InfoBox(
                    label = stringResource(R.string.main_id_label),
                    value = it.id ?: "",
                )
                InfoBox(
                    label = stringResource(R.string.main_pw_label),
                    value = it.pw ?: "",
                )
                InfoBox(
                    label = stringResource(R.string.main_nickname_label),
                    value = it.nickname ?: "",
                )
                InfoBox(
                    label = stringResource(R.string.main_mbti_label),
                    value = it.mbti ?: "",
                )
            }
        }
    }
}