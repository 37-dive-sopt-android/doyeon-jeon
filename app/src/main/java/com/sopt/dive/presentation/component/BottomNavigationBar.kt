package com.sopt.dive.presentation.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.dive.R
import com.sopt.dive.presentation.theme.Background
import com.sopt.dive.presentation.theme.Black3
import com.sopt.dive.presentation.theme.Coral
import com.sopt.dive.presentation.theme.Typography

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    // 네비게이션 아이템이 Row 내부에 들어갈 수 있도록
    // modifier보다 밑에 있어야 람다 전달이 가능함!
    content: @Composable RowScope.() -> Unit,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth(),
        color = Background,
        shape = RoundedCornerShape(100.dp),
        border = BorderStroke(
            width = 2.dp,
            color = Coral.copy(alpha = 0.6f)
        )
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 12.dp),
        ) {
            content()
        }
    }
}

@Composable
fun BottomNavigationBarItem(
    isSelected: Boolean,
    onClick: () -> Unit,
    @DrawableRes icon: Int,
    label: String,
    modifier: Modifier = Modifier,
    selectedColor: Color? = null,
    unselectedColor: Color? = null,
) {
    val selectedColor = selectedColor ?: Coral
    val unselectedColor = unselectedColor ?: Black3

    Column(
        modifier = modifier
            .background(Color.Transparent)
            .clickable(
                // 리플 효과 제거
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick.invoke() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = icon),
            tint = if (isSelected) selectedColor else unselectedColor,
            contentDescription = label,
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.height(4.dp))
        Text(
            label,
            style = Typography.bodySmall,
            color = if (isSelected) selectedColor else unselectedColor,
        )
    }
}

@Preview
@Composable
private fun BottomNavigationBarPreview() {
    BottomNavigationBar {
        BottomNavigationBarItem(
            isSelected = true,
            onClick = {},
            icon = R.drawable.ic_home,
            label = "홈",
            modifier = Modifier.weight(1f)
        )
        BottomNavigationBarItem(
            isSelected = false,
            onClick = {},
            icon = R.drawable.ic_search,
            label = "검색",
            modifier = Modifier.weight(1f)
        )
        BottomNavigationBarItem(
            isSelected = false,
            onClick = {},
            icon = R.drawable.ic_profile,
            label = "프로필",
            modifier = Modifier.weight(1f)
        )
    }
}