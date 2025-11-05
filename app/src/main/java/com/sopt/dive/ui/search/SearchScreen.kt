package com.sopt.dive.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.sopt.dive.R
import com.sopt.dive.ui.component.DiveButton
import com.sopt.dive.ui.component.FlippableCard
import com.sopt.dive.ui.theme.Black2
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
    var isFlipping by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "여기에 만드는 거 맞나?",
            style = Typography.bodySmall,
            color = Black2
        )
        FlippableCard(
            isFlipping = isFlipping
        )
        DiveButton(
            content = if (isFlipping) stringResource(R.string.card_end_button) else stringResource(R.string.card_start_button),
            onClick = { isFlipping = !isFlipping },
        )
    }
}

@Preview
@Composable
private fun SearchScreenPreview() {
    SearchScreen(
        modifier = Modifier.fillMaxSize()
    )
}