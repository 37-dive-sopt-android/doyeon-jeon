package com.sopt.dive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sopt.dive.ui.component.InfoBox
import com.sopt.dive.ui.component.ProfileBox
import com.sopt.dive.ui.theme.Background
import com.sopt.dive.ui.theme.DiveTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id = intent.getStringExtra("id") ?: ""
        val pw = intent.getStringExtra("pw") ?: ""
        val nickname = intent.getStringExtra("nickname") ?: ""
        val mbti = intent.getStringExtra("mbti") ?: ""

        enableEdgeToEdge()
        setContent {
            DiveTheme {
                MainScreen(id, pw, nickname, mbti)
            }
        }
    }
}

@Composable
fun MainScreen(
    id: String,
    pw: String,
    nickname: String,
    mbti: String,
) {
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
                ProfileBox(
                    image = R.drawable.chopper,
                    name = "도도",
                    bio = "도도가 뛰면 도도도도도도도",
                )
                Spacer(Modifier.height(40.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(28.dp)
                ) {
                    InfoBox(
                        label = "ID",
                        value = id,
                    )
                    InfoBox(
                        label = "PW",
                        value = pw,
                    )
                    InfoBox(
                        label = "NICKNAME",
                        value = nickname,
                    )
                    InfoBox(
                        label = "MBTI",
                        value = mbti,
                    )
                }
            }
        }
    }
}
