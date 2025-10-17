package com.sopt.dive.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import com.sopt.dive.ui.theme.Black3
import com.sopt.dive.ui.theme.Typography

@Composable
fun TextButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Text(
        label,
        style = Typography.bodySmall,
        color = Black3,
        textDecoration = TextDecoration.Underline,
        modifier = modifier.clickable(
            // 리플효과 제거
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) {
            onClick.invoke()
        }
    )
}

@Preview
@Composable
fun TextButtonPreview() {
    TextButton(
        label = "야야야",
        onClick = {},
    )
}