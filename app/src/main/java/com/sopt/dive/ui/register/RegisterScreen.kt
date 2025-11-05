package com.sopt.dive.ui.register

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import com.sopt.dive.R
import com.sopt.dive.data.datastore.DataStoreKeys
import com.sopt.dive.data.datastore.dataStore
import com.sopt.dive.ui.component.AuthButton
import com.sopt.dive.ui.component.AuthInputField
import com.sopt.dive.ui.component.ScreenTitle
import kotlinx.coroutines.launch

@Composable
fun RegisterRoute(
    popToLogin: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var id by remember { mutableStateOf("") }
    var pw by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }
    var mbti by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    fun onRegisterClick() {
        // 아이디가 유효하지 않은 경우
        if (id.length < 6 || id.length > 10) {
            Toast.makeText(
                context,
                context.getString(R.string.register_id_fail_message),
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        // 비밀번호가 유효하지 않은 경우
        if (pw.length < 8 || pw.length > 12) {
            Toast.makeText(
                context,
                context.getString(R.string.register_pw_fail_message),
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        // 닉네임이 유효하지 않은 경우
        if (nickname.isBlank()) {
            Toast.makeText(
                context,
                context.getString(R.string.register_nickname_fail_message),
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        // MBTI가 유효하지 않은 경우
        if (mbti.length != 4) {
            Toast.makeText(
                context,
                context.getString(R.string.register_mbti_fail_message),
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        // 위 케이스 모두 통과했다면 > 회원가입
        scope.launch {
            // 유저 정보 저장
            context.dataStore.edit { user ->
                user[DataStoreKeys.IS_LOGGED_IN] = false
                user[DataStoreKeys.ID] = id
                user[DataStoreKeys.PW] = pw
                user[DataStoreKeys.NICKNAME] = nickname
                user[DataStoreKeys.MBTI] = mbti
            }
            // 로그인 페이지로 돌아가기
            popToLogin()
            // 회원가입 완료 토스트
            Toast.makeText(
                context,
                context.getString(R.string.register_success_message), Toast.LENGTH_SHORT
            ).show()
        }
    }

    RegisterScreen(
        focusManager = focusManager,
        id = id,
        onIdChanged = { id = it },
        pw = pw,
        onPwChanged = { pw = it },
        nickname = nickname,
        onNicknameChanged = { nickname = it },
        mbti = mbti,
        onMbtiChanged = { mbti = it },
        onRegisterClick = { onRegisterClick() },
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
    mbti: String,
    onMbtiChanged: (String) -> Unit,
    onRegisterClick: () -> Unit,
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
                label = stringResource(R.string.register_mbti_label),
                value = mbti,
                onValueChanged = onMbtiChanged,
                placeholder = stringResource(R.string.register_mbti_placeholder),
                focusManager = focusManager,
            )
        }
        Spacer(Modifier.weight(1f))
        // 회원가입 버튼
        AuthButton(
            content = stringResource(R.string.register_button),
            onClick = onRegisterClick,
            modifier = Modifier.fillMaxWidth()
        )
    }
}