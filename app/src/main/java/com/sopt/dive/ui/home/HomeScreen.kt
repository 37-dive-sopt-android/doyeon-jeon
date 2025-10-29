package com.sopt.dive.ui.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sopt.dive.R
import com.sopt.dive.data.myProfile
import com.sopt.dive.ui.component.ProfileCard

@Composable
fun HomeScreen() {
    LazyColumn(
        modifier = Modifier.padding(
            horizontal = 20.dp,
            vertical = 24.dp
        )
    ) {
        item {
            ProfileCard(
                profile = myProfile
            )
        }
    }
}