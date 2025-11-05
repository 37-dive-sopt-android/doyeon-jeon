package com.sopt.dive.ui.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sopt.dive.R
import com.sopt.dive.data.model.AccountInfo
import com.sopt.dive.data.datastore.DataStoreKeys
import com.sopt.dive.data.datastore.dataStore
import com.sopt.dive.ui.component.AuthButton
import com.sopt.dive.ui.component.AuthInputField
import com.sopt.dive.ui.component.DiveTextButton
import com.sopt.dive.ui.component.ScreenTitle
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navigateToHome: () -> Unit,
    navigateToRegister: () -> Unit,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // 본 화면에서 유저가 입력한 값
    var inputId by remember { mutableStateOf("") }
    var inputPw by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    // 저장된 id/pw 불러오기
    val accountInfo by context.dataStore.data
        .map { preferences ->
            AccountInfo(
                id = preferences[DataStoreKeys.ID],
                pw = preferences[DataStoreKeys.PW],
            )
        }
        .collectAsStateWithLifecycle(null)

    Column(
        modifier = Modifier.padding(
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
                onValueChanged = { inputId = it },
                placeholder = stringResource(R.string.login_id_placeholder),
                focusManager = focusManager,
                imeAction = ImeAction.Next,
            )
            AuthInputField(
                label = stringResource(R.string.login_pw_label),
                value = inputPw,
                onValueChanged = { inputPw = it },
                placeholder = stringResource(R.string.login_pw_placeholder),
                isPw = true,
                focusManager = focusManager,
            )
        }
        Spacer(Modifier.weight(1f))
        // 회원가입 하러 가는 버튼
        DiveTextButton(
            label = stringResource(R.string.login_to_register_button),
            onClick = { navigateToRegister() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(16.dp))
        // 로그인 버튼
        AuthButton(
            content = stringResource(R.string.login_button),
            onClick = {
                // 회원가입 안 한 경우
                if (accountInfo == null) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.login_need_register_fail_message),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@AuthButton
                }
                // id나 pw가 일치하지 않는 경우
                if (inputId != accountInfo!!.id || inputPw != accountInfo!!.pw) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.login_invalid_fail_message),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    return@AuthButton
                }
                // 위 케이스 모두 통과했다면 > 로그인
                scope.launch {
                    // 로그인 상태 저장
                    context.dataStore.edit { user ->
                        user[DataStoreKeys.IS_LOGGED_IN] = true
                    }
                    // 홈으로 이동
                    navigateToHome()
                    // 로그인 완료 토스트
                    Toast.makeText(
                        context,
                        context.getString(R.string.login_success_message), Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}