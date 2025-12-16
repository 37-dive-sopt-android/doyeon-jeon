package com.sopt.dive.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.dive.presentation.theme.Green
import com.sopt.dive.presentation.theme.Typography

@Composable
fun DiveChip(
    content: String,
    style: DiveChipStyle,
    modifier: Modifier = Modifier,
    maxLines: Int = 1,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(100.dp),
        border = BorderStroke(
            width = 0.5.dp,
            color = style.strokeColor
        ),
        color = style.backgroundColor,
    ) {
        Text(
            content,
            style = Typography.bodySmall,
            color = style.contentColor,
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
        )
    }
}

enum class DiveChipStyle(
    val contentColor: Color,
    val backgroundColor: Color,
    val strokeColor: Color,
) {
    GreenStyle(
        contentColor = Green,
        backgroundColor = Green.copy(alpha = 0.1f),
        strokeColor = Green
    )
}

@Preview
@Composable
private fun DiveChipPreview() {
    DiveChip(
        content = "🎵 안뇽",
        style = DiveChipStyle.GreenStyle,
    )
}