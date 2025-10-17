package com.sopt.dive

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.sopt.dive.ui.component.AuthButton
import com.sopt.dive.ui.component.AuthInputField
import com.sopt.dive.ui.component.ScreenTitle
import com.sopt.dive.ui.theme.Background
import com.sopt.dive.ui.theme.DiveTheme

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DiveTheme {
                RegisterScreen(
                    // composable 안에서 정의가 안 돼서 외부에서 전달
                    onRegisterClick = { id, pw, nickname, mbti ->
                        val intent = Intent()
                            .putExtra("id", id)
                            .putExtra("pw", pw)
                            .putExtra("nickname", nickname)
                            .putExtra("mbti", mbti)
                        setResult(RESULT_OK, intent)
                        finish()
                    }
                )
            }
        }
    }
}

@Composable
fun RegisterScreen(
    onRegisterClick: (String, String, String, String) -> Unit,
) {
    val context = LocalContext.current

    var id by remember { mutableStateOf("") }
    var pw by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }
    var mbti by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Background)
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
                        label = "ID",
                        value = id,
                        onValueChanged = { id = it },
                        placeholder = "아이디를 입력해주세요",
                        focusManager = focusManager,
                        imeAction = ImeAction.Next,
                    )
                    AuthInputField(
                        label = "PW",
                        value = pw,
                        onValueChanged = { pw = it },
                        placeholder = "비밀번호를 입력해주세요",
                        isPw = true,
                        focusManager = focusManager,
                        imeAction = ImeAction.Next,
                    )
                    AuthInputField(
                        label = "NICKNAME",
                        value = nickname,
                        onValueChanged = { nickname = it },
                        placeholder = "닉네임을 입력해주세요",
                        focusManager = focusManager,
                        imeAction = ImeAction.Next,
                    )
                    AuthInputField(
                        label = "MBTI",
                        value = mbti,
                        onValueChanged = { mbti = it },
                        placeholder = "MBTI를 입력해주세요",
                        focusManager = focusManager,
                    )
                }
                Spacer(Modifier.weight(1f))
                // 회원가입 버튼
                AuthButton(
                    content = "회원가입하기",
                    onClick = {
                        // 아이디가 유효하지 않은 경우
                        if (id.length < 6 || id.length > 10) {
                            Toast.makeText(context, "아이디는 6~10자 입력해주세요", Toast.LENGTH_SHORT).show()
                            return@AuthButton
                        }
                        // 비밀번호가 유효하지 않은 경우
                        if (pw.length < 8 || pw.length > 12) {
                            Toast.makeText(context, "비밀번호는 8~12자 입력해주세요", Toast.LENGTH_SHORT).show()
                            return@AuthButton
                        }
                        // 닉네임이 유효하지 않은 경우
                        if (nickname.isEmpty() || nickname.isBlank()) {
                            Toast.makeText(context, "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show()
                            return@AuthButton
                        }
                        // MBTI가 유효하지 않은 경우
                        if (mbti.length != 4) {
                            Toast.makeText(context, "MBTI는 4자 입력해주세요", Toast.LENGTH_SHORT).show()
                            return@AuthButton
                        }
                        // 위 케이스 모두 통과했다면 > 회원가입
                        onRegisterClick(id, pw, nickname, mbti)
                        Toast.makeText(context, "회원가입에 성공했어요", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}