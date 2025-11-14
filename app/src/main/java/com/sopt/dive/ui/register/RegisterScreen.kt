package com.sopt.dive.ui.register

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sopt.dive.R
import com.sopt.dive.ui.component.AuthInputField
import com.sopt.dive.ui.component.DiveButton
import com.sopt.dive.ui.component.ScreenTitle

@Composable
fun RegisterRoute(
    popToLogin: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = viewModel(
        factory = RegisterViewModel.Factory
    ),
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect {
            when (it) {
                is RegisterSideEffect.PopToLogin -> popToLogin()
                is RegisterSideEffect.ShowToast -> Toast.makeText(
                    context,
                    context.getString(it.message),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    RegisterScreen(
        focusManager = focusManager,
        id = uiState.id,
        onIdChanged = viewModel::onIdChanged,
        pw = uiState.pw,
        onPwChanged = viewModel::onPwChanged,
        nickname = uiState.nickname,
        onNicknameChanged = viewModel::onNicknameChanged,
        email = uiState.email,
        onEmailChanged = viewModel::onEmailChanged,
        age = uiState.age,
        onAgeChanged = viewModel::onAgeChanged,
        onRegisterClick = viewModel::onRegisterClick,
        isRegisterButtonEnabled = uiState.isRegisterButtonEnabled,
        modifier = modifier
    )
}

@Composable
fun RegisterScreen(
    focusManager: FocusManager,
    id: String,
    onIdChanged: (String) -> Unit,
    pw: String,
    onPwChanged: (String) -> Unit,
    nickname: String,
    onNicknameChanged: (String) -> Unit,
    email: String,
    onEmailChanged: (String) -> Unit,
    age: String,
    onAgeChanged: (String) -> Unit,
    onRegisterClick: () -> Unit,
    isRegisterButtonEnabled: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(
            vertical = 24.dp,
            horizontal = 20.dp
        )
    ) {
        // 타이틀
        ScreenTitle("SIGN UP")
        Spacer(Modifier.height(40.dp))
        // 입력 구좌
        Column(
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            AuthInputField(
                label = stringResource(R.string.register_id_label),
                value = id,
                onValueChanged = onIdChanged,
                placeholder = stringResource(R.string.register_id_placeholder),
                focusManager = focusManager,
                imeAction = ImeAction.Next,
            )
            AuthInputField(
                label = stringResource(R.string.register_pw_label),
                value = pw,
                onValueChanged = onPwChanged,
                placeholder = stringResource(R.string.register_pw_placeholder),
                isPw = true,
                focusManager = focusManager,
                imeAction = ImeAction.Next,
            )
            AuthInputField(
                label = stringResource(R.string.register_nickname_label),
                value = nickname,
                onValueChanged = onNicknameChanged,
                placeholder = stringResource(R.string.register_nickname_placeholder),
                focusManager = focusManager,
                imeAction = ImeAction.Next,
            )
            AuthInputField(
                label = stringResource(R.string.register_email_label),
                value = email,
                onValueChanged = onEmailChanged,
                placeholder = stringResource(R.string.register_email_placeholder),
                focusManager = focusManager,
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email
            )
            AuthInputField(
                label = stringResource(R.string.register_age_label),
                value = age,
                onValueChanged = onAgeChanged,
                placeholder = stringResource(R.string.register_age_placeholder),
                focusManager = focusManager,
                keyboardType = KeyboardType.Number
            )
        }
        Spacer(Modifier.weight(1f))
        // 회원가입 버튼
        DiveButton(
            content = stringResource(R.string.register_button),
            onClick = onRegisterClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = isRegisterButtonEnabled
        )
    }
}
