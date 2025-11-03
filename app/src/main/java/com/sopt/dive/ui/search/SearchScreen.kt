package com.sopt.dive.ui.search

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sopt.dive.ui.theme.Typography

@Composable
fun SearchRoute(
    modifier: Modifier = Modifier,
) {
    SearchScreen(
        modifier = modifier
    )
}

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
) {
    Text("검색", style = Typography.titleLarge, modifier = modifier)
}