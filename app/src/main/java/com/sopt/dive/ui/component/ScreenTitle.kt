package com.sopt.dive.ui.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.sopt.dive.ui.theme.Coral
import com.sopt.dive.ui.theme.Typography

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