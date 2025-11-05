package com.sopt.dive.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.dive.ui.theme.Coral
import com.sopt.dive.ui.theme.Typography

@Composable
fun DiveButton(
    content: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Coral.copy(alpha = if (enabled) 1f else 0.4f),
        ),
    ) {
        Text(
            content,
            style = Typography.titleSmall
        )
    }
}

@Preview
@Composable
private fun AuthButtonPreview() {
    DiveButton(
        content = "안녕",
        onClick = { },
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview
@Composable
private fun AuthButtonDisabledPreview() {
    DiveButton(
        content = "안녕",
        onClick = { },
        modifier = Modifier.fillMaxWidth(),
        enabled = false
    )
}