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
fun AuthButton(
    content: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Coral,
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
fun AuthButtonPreview() {
    AuthButton(
        content = "안녕",
        onClick = { },
        modifier = Modifier.fillMaxWidth()
    )
}