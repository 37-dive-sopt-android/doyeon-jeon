package com.sopt.dive.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.dive.data.homeProfiles
import com.sopt.dive.data.myProfile
import com.sopt.dive.ui.component.ProfileCard
import com.sopt.dive.ui.theme.Black3

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier.padding(
            horizontal = 20.dp,
            vertical = 24.dp
        )
    ) {
        ProfileCard(
            profile = myProfile
        )
        HorizontalDivider(
            thickness = 2.dp,
            color = Black3.copy(alpha = 0.6f),
            modifier = Modifier.padding(top = 20.dp)
        )
        LazyColumn {
            items(homeProfiles) {
                ProfileCard(
                    profile = it,
                    modifier = Modifier.padding(top = 20.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}