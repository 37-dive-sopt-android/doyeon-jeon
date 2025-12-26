package com.sopt.dive.presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.dp
import com.sopt.dive.R
import com.sopt.dive.presentation.component.DiveButton
import com.sopt.dive.presentation.component.DoubleFlippableCard
import com.sopt.dive.presentation.component.FlippableCard
import com.sopt.dive.presentation.theme.Black2
import com.sopt.dive.presentation.theme.Typography

@Composable
fun SearchRoute(
    modifier: Modifier = Modifier,
    id: Int,
    category: String?,
    keyword: String?,
) {
    SearchScreen(
        modifier = modifier,
        id = id,
        category = category,
        keyword = keyword
    )
}

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    id: Int,
    category: String? = null,
    keyword: String? = null,
) {
    var isFlipping by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 40.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "id = $id, category = $category, keyword = $keyword",
            style = Typography.titleMedium,
            color = Black2
        )
        FlippableCard(
            isFlipping = isFlipping
        )
        DiveButton(
            content = if (isFlipping) stringResource(R.string.card_end_button) else stringResource(R.string.card_start_button),
            onClick = { isFlipping = !isFlipping },
        )
        DoubleFlippableCard()
    }
}

@Preview
@Composable
private fun SearchScreenPreview() {
    SearchScreen(
        modifier = Modifier.fillMaxSize(),
        id = 3,
    )
}