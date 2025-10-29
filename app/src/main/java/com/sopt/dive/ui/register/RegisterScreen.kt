package com.sopt.dive.ui.register

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import com.sopt.dive.R
import com.sopt.dive.data.DataStoreKeys
import com.sopt.dive.data.dataStore
import com.sopt.dive.ui.component.AuthButton
import com.sopt.dive.ui.component.AuthInputField
import com.sopt.dive.ui.component.ScreenTitle
import com.sopt.dive.ui.theme.Background
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    popToLogin: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var id by remember { mutableStateOf("") }
    var pw by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }
    var mbti by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier.padding(
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
                        onValueChanged = { id = it },
                        placeholder = stringResource(R.string.register_id_placeholder),
                        focusManager = focusManager,
                        imeAction = ImeAction.Next,
                    )
                    AuthInputField(
                        label = stringResource(R.string.register_pw_label),
                        value = pw,
                        onValueChanged = { pw = it },
                        placeholder = stringResource(R.string.register_pw_placeholder),
                        isPw = true,
                        focusManager = focusManager,
                        imeAction = ImeAction.Next,
                    )
                    AuthInputField(
                        label = stringResource(R.string.register_nickname_label),
                        value = nickname,
                        onValueChanged = { nickname = it },
                        placeholder = stringResource(R.string.register_nickname_placeholder),
                        focusManager = focusManager,
                        imeAction = ImeAction.Next,
                    )
                    AuthInputField(
                        label = stringResource(R.string.register_mbti_label),
                        value = mbti,
                        onValueChanged = { mbti = it },
                        placeholder = stringResource(R.string.register_mbti_placeholder),
                        focusManager = focusManager,
                    )
                }
                Spacer(Modifier.weight(1f))
                // 회원가입 버튼
                AuthButton(
                    content = stringResource(R.string.register_button),
                    onClick = {
                        // 아이디가 유효하지 않은 경우
                        if (id.length < 6 || id.length > 10) {
                            Toast.makeText(
                                context,
                                context.getString(R.string.register_id_fail_message),
                                Toast.LENGTH_SHORT
                            ).show()
                            return@AuthButton
                        }
                        // 비밀번호가 유효하지 않은 경우
                        if (pw.length < 8 || pw.length > 12) {
                            Toast.makeText(
                                context,
                                context.getString(R.string.register_pw_fail_message),
                                Toast.LENGTH_SHORT
                            ).show()
                            return@AuthButton
                        }
                        // 닉네임이 유효하지 않은 경우
                        if (nickname.isBlank()) {
                            Toast.makeText(
                                context,
                                context.getString(R.string.register_nickname_fail_message),
                                Toast.LENGTH_SHORT
                            ).show()
                            return@AuthButton
                        }
                        // MBTI가 유효하지 않은 경우
                        if (mbti.length != 4) {
                            Toast.makeText(
                                context,
                                context.getString(R.string.register_mbti_fail_message),
                                Toast.LENGTH_SHORT
                            ).show()
                            return@AuthButton
                        }
                        // 위 케이스 모두 통과했다면 > 회원가입 (유저정보 저장)
                        scope.launch {
                            context.dataStore.edit { user ->
                                user[DataStoreKeys.IS_LOGGED_IN] = false
                                user[DataStoreKeys.ID] = id
                                user[DataStoreKeys.PW] = pw
                                user[DataStoreKeys.NICKNAME] = nickname
                                user[DataStoreKeys.MBTI] = mbti
                            }
                        }
                        // 로그인 페이지로 돌아가기
                        popToLogin()
                        // 회원가입 완료 토스트
                        Toast.makeText(
                            context,
                            context.getString(R.string.register_success_message), Toast.LENGTH_SHORT
                        ).show()
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}