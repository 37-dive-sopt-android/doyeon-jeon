package com.sopt.dive

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import com.sopt.dive.ui.component.AuthButton
import com.sopt.dive.ui.component.AuthInputField
import com.sopt.dive.ui.component.ScreenTitle
import com.sopt.dive.ui.component.TextButton
import com.sopt.dive.ui.theme.Background
import com.sopt.dive.ui.theme.DiveTheme
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DiveTheme {
                LoginScreen()
            }
        }
    }
}

@Composable
fun LoginScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // 저장된 데이터 불러오기 ~
    val userId by context.dataStore.data
        .map { it[DataStoreKeys.ID] }
        .collectAsState(initial = null)

    val userPw by context.dataStore.data
        .map { it[DataStoreKeys.PW] }
        .collectAsState(initial = null)

    val userNickname by context.dataStore.data
        .map { it[DataStoreKeys.NICKNAME] }
        .collectAsState(initial = null)

    val userMbti by context.dataStore.data
        .map { it[DataStoreKeys.MBTI] }
        .collectAsState(initial = null)
    // ~ 저장된 데이터 불러오기

    // 저장된 유저 정보 있다면 메인 화면 랜딩
    LaunchedEffect(userId) {
        if (userId != null && userPw != null && userNickname != null && userMbti != null) {
            val intent = Intent(context, MainActivity::class.java).apply {
                // 뒤로가기 해도 로그인 화면으로 못돌아오게 flags 달기
                flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
                // 메인 화면으로 회원 정보 넘겨주기
                .putExtra("id", userId)
                .putExtra("pw", userPw)
                .putExtra("nickname", userNickname)
                .putExtra("mbti", userMbti)
            context.startActivity(intent)
        }
    }

    // 회원가입 완료했는지 확인하기 위한 값
    var isRegistered by remember { mutableStateOf(false) }

    // 회원가입 화면에서 넘겨받은 값
    var id by remember { mutableStateOf("") }
    var pw by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }
    var mbti by remember { mutableStateOf("") }

    // 본 화면에서 유저가 입력한 값
    var inputId by remember { mutableStateOf("") }
    var inputPw by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                id = result.data?.getStringExtra("id") ?: "-"
                pw = result.data?.getStringExtra("pw") ?: "-"
                nickname = result.data?.getStringExtra("nickname") ?: "-"
                mbti = result.data?.getStringExtra("mbti") ?: "-"
                isRegistered = true
                Log.d("LOGIN", "[데이터 가져옴] id = $id, pw = $pw, nickname = $nickname, mbit = $mbti")
            }
        }

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
                ScreenTitle("Welcome To SOPT")
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
                        label = "ID",
                        value = inputId,
                        onValueChanged = { inputId = it },
                        placeholder = "아이디를 입력해주세요",
                        focusManager = focusManager,
                        imeAction = ImeAction.Next,
                    )
                    AuthInputField(
                        label = "PW",
                        value = inputPw,
                        onValueChanged = { inputPw = it },
                        placeholder = "비밀번호를 입력해주세요",
                        isPw = true,
                        focusManager = focusManager,
                    )
                }
                Spacer(Modifier.weight(1f))
                // 회원가입 하러 가는 버튼
                TextButton(
                    label = "회원가입하기",
                    onClick = {
                        val intent = Intent(context, RegisterActivity::class.java)
                        // 가입 완료 후 돌아왔을 때 데이터 전달받기 위해 laucher 사용
                        launcher.launch(intent)
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(Modifier.height(16.dp))
                // 로그인 버튼
                AuthButton(
                    content = "로그인하기",
                    onClick = {
                        // 회원가입 안 한 경우
                        if (!isRegistered) {
                            Toast.makeText(context, "회원가입 후 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                            return@AuthButton
                        }
                        // id나 pw가 일치하지 않는 경우
                        if (inputId != id || inputPw != pw) {
                            Toast.makeText(context, "아이디 또는 비밀번호가 일치하지 않아요", Toast.LENGTH_SHORT)
                                .show()
                            return@AuthButton
                        }
                        // 위 케이스 모두 통과했다면 > 로그인

                        // DataStore에 유저 정보 저장하기
                        scope.launch {
                            context.dataStore.edit { user ->
                                user[DataStoreKeys.ID] = id
                                user[DataStoreKeys.PW] = pw
                                user[DataStoreKeys.NICKNAME] = nickname
                                user[DataStoreKeys.MBTI] = mbti
                            }
                        }

                        val intent = Intent(context, MainActivity::class.java).apply {
                            // 뒤로가기 해도 로그인 화면으로 못돌아오게 flags 달기
                            flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                            // 메인 화면으로 회원 정보 넘겨주기
                            .putExtra("id", id)
                            .putExtra("pw", pw)
                            .putExtra("nickname", nickname)
                            .putExtra("mbti", mbti)
                        context.startActivity(intent)

                        Toast.makeText(context, "로그인에 성공했어요", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}