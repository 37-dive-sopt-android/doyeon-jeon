package com.sopt.dive.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.dive.presentation.theme.Black1
import com.sopt.dive.presentation.theme.DiveTheme
import com.sopt.dive.presentation.theme.Typography
import com.sopt.dive.presentation.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiveBottomSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    containerColor: Color = White,
    scrimColor: Color = Black1.copy(alpha = 0.32f),
    dragHandle: @Composable (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        sheetState = sheetState,
        shape = RoundedCornerShape(
            topStart = 20.dp,
            topEnd = 20.dp
        ),
        containerColor = containerColor,
        scrimColor = scrimColor,
        dragHandle = dragHandle,
    )
    {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 20.dp),
        ) {
            Text(
                text = "X",
                style = Typography.titleMedium
            )
            content()
            DiveButton(
                content = "hi",
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun DiveBottomSheetPreview() {
    DiveTheme {
        DiveBottomSheet(
            onDismissRequest = { },
            content = { Text("우하하".repeat(10)) }
        )
    }
}