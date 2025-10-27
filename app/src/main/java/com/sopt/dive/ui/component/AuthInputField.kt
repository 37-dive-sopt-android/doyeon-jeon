package com.sopt.dive.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.sopt.dive.ui.theme.Black1
import com.sopt.dive.ui.theme.Black2
import com.sopt.dive.ui.theme.Black3
import com.sopt.dive.ui.theme.Typography

@Composable
fun AuthInputField(
    label: String,
    value: String,
    onValueChanged: (String) -> Unit,
    placeholder: String,
    focusManager: FocusManager,
    modifier: Modifier = Modifier,
    isPw: Boolean = false,
    maxLines: Int = 1,
    imeAction: ImeAction = ImeAction.Done,
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
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = value,
            onValueChange = onValueChanged,
            textStyle = Typography.bodyMedium.copy(color = Black2),
            singleLine = true,
            visualTransformation = if (isPw) PasswordVisualTransformation() else VisualTransformation.None,
            maxLines = maxLines,
            // imeAction 적용
            keyboardOptions = KeyboardOptions(
                imeAction = imeAction
            ),
            // imeAction에 따른 기능 정의
            // - Done > 포커스 해제
            // - Next > 아래에 위치한 필드로 포커스 이동
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus(true)
                },
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            decorationBox = { innerTextField ->
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        if (value.isEmpty())
                            Text(
                                placeholder,
                                style = Typography.bodyMedium,
                                color = Black3
                            )
                        innerTextField()
                    }
                }
            }
        )
    }
}

//@Preview(heightDp = 80)
//@Composable
//fun AuthInputFieldPreview() {
//    AuthInputField(
//        label = "안녕",
//        value = "하니?",
//        onValueChanged = {},
//        placeholder = "",
//    )
//}