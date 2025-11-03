package com.sopt.dive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sopt.dive.ui.component.BottomNavigationBar
import com.sopt.dive.ui.component.BottomNavigationBarItem
import com.sopt.dive.ui.navigation.NavBarDestination
import com.sopt.dive.ui.navigation.diveNavGraph
import com.sopt.dive.ui.splash.Splash
import com.sopt.dive.ui.theme.Background
import com.sopt.dive.ui.theme.DiveTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            DiveTheme {
                val navController = rememberNavController()

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                // NavBar 연결된 화면일 때만 보여줌
                val showNavBar = NavBarDestination.entries.any {
                    it.route::class.qualifiedName == currentRoute
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (showNavBar) {
                            DiveBottomNavigationBar(
                                destinations = NavBarDestination.entries,
                                currentDestination = currentRoute,
                                onNavigate = {
                                    navController.navigate(it) {
                                        // 이동한 화면과 스택 밑바닥 화면만 남기고 사이 스택 제거
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            // Search > Profile > Search로 돌아와도 Search의 화면 유지될 수 있도록 상태 저장
                                            saveState = true
                                        }
                                        // 최상위 스택이 중복으로 쌓이는 일 방지
                                        launchSingleTop = true
                                        // saveState로 저장한 상태 불러옴
                                        restoreState = true
                                    }
                                },
                                modifier = Modifier
                                    .padding(horizontal = 20.dp, vertical = 0.dp)
                                    .navigationBarsPadding()
                            )
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Background)
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = Splash,
                        ) {
                            diveNavGraph(navController, innerPadding)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DiveBottomNavigationBar(
    destinations: List<NavBarDestination>,
    currentDestination: String?,
    onNavigate: (Any) -> Unit,
    modifier: Modifier = Modifier,
) {
    BottomNavigationBar(
        modifier = modifier
    ) {
        destinations.forEach { destination ->
            // Home -> com.sopt.dive.ui.home.Home로 표현하여 currentDestination과 동일한지 비교하기 위해
            // class.qualifiedName 사용
            val isSelected = destination.route::class.qualifiedName == currentDestination

            BottomNavigationBarItem(
                isSelected = isSelected,
                onClick = { onNavigate(destination.route) },
                icon = destination.icon,
                label = destination.label,
                modifier = Modifier.weight(1f),
                selectedColor = destination.selectedColor,
                unselectedColor = destination.unselectedColor
            )
        }
    }
}