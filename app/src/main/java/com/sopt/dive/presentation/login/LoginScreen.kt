package com.sopt.dive.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sopt.dive.R
import com.sopt.dive.core.util.showServerErrorToast
import com.sopt.dive.core.util.showToast
import com.sopt.dive.presentation.component.AuthInputField
import com.sopt.dive.presentation.component.DiveButton
import com.sopt.dive.presentation.component.DiveTextButton
import com.sopt.dive.presentation.component.ScreenTitle
import com.sopt.dive.presentation.login.LoginContract.Event.OnIdChanged
import com.sopt.dive.presentation.login.LoginContract.Event.OnLoginBtnClicked
import com.sopt.dive.presentation.login.LoginContract.Event.OnPwChanged
import com.sopt.dive.presentation.login.LoginContract.SideEffect.NavigateToHome
import com.sopt.dive.presentation.login.LoginContract.SideEffect.NavigateToRegister
import com.sopt.dive.presentation.login.LoginContract.SideEffect.ShowErrorToast
import com.sopt.dive.presentation.login.LoginContract.SideEffect.ShowToast
import com.sopt.dive.presentation.theme.DiveTheme

@Composable
fun LoginRoute(
    navigateToHome: () -> Unit,
    navigateToRegister: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel(),
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect {
            when (it) {
                is NavigateToHome -> navigateToHome()
                is NavigateToRegister -> navigateToRegister()
                is ShowToast -> context.showToast(it.message)
                is ShowErrorToast -> context.showServerErrorToast(it.e)
            }
        }
    }

    LoginScreen(
        focusManager = focusManager,
        inputId = uiState.inputId,
        onInputIdChanged = { value -> viewModel.setEvent(OnIdChanged(value)) },
        inputPw = uiState.inputPw,
        onInputPwChanged = { value -> viewModel.setEvent(OnPwChanged(value)) },
        onRegisterClick = navigateToRegister,
        onLoginClick = { viewModel.setEvent(OnLoginBtnClicked) },
        isLoginButtonEnabled = uiState.isLoginButtonEnabled,
        modifier = modifier
    )
}

@Composable
fun LoginScreen(
    focusManager: FocusManager,
    inputId: String,
    onInputIdChanged: (String) -> Unit,
    inputPw: String,
    onInputPwChanged: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit,
    isLoginButtonEnabled: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(
            vertical = 24.dp,
            horizontal = 20.dp
        )
    ) {
        // 타이틀
        ScreenTitle(stringResource(R.string.login_title))
        // 왜 Spacer?
        // - 전혀 다른 ui가 쌓이는 거라
        Spacer(Modifier.height(40.dp))
        // 입력 구좌
        Column(
            // 왜 Arrangement?
            // - 일관되게 쌓여야 하는 것들이라
            // - 지금은 두 개 뿐이지만 만약 InputField가 추가된다면? 같은 여백을 두고 쌓일 거라
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            AuthInputField(
                label = stringResource(R.string.login_id_label),
                value = inputId,
                onValueChanged = onInputIdChanged,
                placeholder = stringResource(R.string.login_id_placeholder),
                focusManager = focusManager,
                imeAction = ImeAction.Next,
            )
            AuthInputField(
                label = stringResource(R.string.login_pw_label),
                value = inputPw,
                onValueChanged = onInputPwChanged,
                placeholder = stringResource(R.string.login_pw_placeholder),
                isPw = true,
                focusManager = focusManager,
            )
        }
        Spacer(Modifier.weight(1f))
        // 회원가입 하러 가는 버튼
        DiveTextButton(
            label = stringResource(R.string.login_to_register_button),
            onClick = onRegisterClick,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(16.dp))
        // 로그인 버튼
        DiveButton(
            content = stringResource(R.string.login_button),
            onClick = onLoginClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = isLoginButtonEnabled
        )
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    DiveTheme {
        LoginScreen(
            focusManager = LocalFocusManager.current,
            inputId = "",
            onInputIdChanged = {},
            inputPw = "",
            onInputPwChanged = {},
            onRegisterClick = {},
            onLoginClick = {},
            isLoginButtonEnabled = false,
        )
    }
}