package com.sopt.dive.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.dive.presentation.theme.Black1
import com.sopt.dive.presentation.theme.Black2
import com.sopt.dive.presentation.theme.Typography

@Composable
fun InfoBox(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            label,
            style = Typography.titleMedium,
            color = Black1
        )
        Spacer(Modifier.height(8.dp))
        Text(
            value,
            style = Typography.bodyMedium,
            color = Black2
        )
    }
}

@Preview
@Composable
fun InfoBoxPreview() {
    InfoBox(
        label = "야야야",
        value = "야야야야야야!!!",
    )
}