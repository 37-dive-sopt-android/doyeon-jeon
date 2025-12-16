package com.sopt.dive.presentation.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.sopt.dive.presentation.theme.Coral
import com.sopt.dive.presentation.theme.Typography

@Composable
fun ScreenTitle(
    text: String
) {
    Text(
        text,
        style = Typography.titleLarge,
        color = Coral
    )
}