package com.sopt.dive.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sopt.dive.data.model.Profile
import com.sopt.dive.ui.component.ProfileCard
import com.sopt.dive.ui.theme.Background
import com.sopt.dive.ui.theme.Black3

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        myProfile = uiState.myProfile,
        homeProfiles = uiState.otherProfiles,
        modifier = modifier
    )
}

@Composable
fun HomeScreen(
    myProfile: Profile?,
    homeProfiles: List<Profile>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 24.dp)
    ) {
        stickyHeader {
            myProfile?.let {
                ProfileHeader(
                    myProfile = it,
                )
            }
        }
        items(homeProfiles) {
            ProfileCard(
                profile = it,
                modifier = Modifier.padding(top = 20.dp)
            )
        }
    }
}

@Composable
private fun ProfileHeader(
    myProfile: Profile,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.background(Background)
    ) {
        ProfileCard(
            profile = myProfile,
        )
        HorizontalDivider(
            thickness = 2.dp,
            color = Black3.copy(alpha = 0.6f),
            modifier = Modifier.padding(top = 20.dp)
        )
    }
}

//@Preview
//@Composable
//fun HomeScreenPreview() {
//    HomeScreen(
//        myProfile = myProfile,
//        homeProfiles = homeProfiles,
//    )
//}