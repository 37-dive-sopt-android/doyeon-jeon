package com.sopt.dive.ui.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.EaseOutExpo
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun FlippableCard(
    isFlipping: Boolean,
    modifier: Modifier = Modifier,
    frontImage: String = "https://i.pinimg.com/1200x/d8/b8/b3/d8b8b3049821ee856b3a87102b0235cb.jpg",
    backImage: String = "https://i.pinimg.com/736x/56/12/97/561297f7e47df59a250c851382d0fab5.jpg",
    width: Dp = 200.dp,
) {
    val rotation = remember { Animatable(0f) }

    // isFlipping 조건이 변경될 때 실행
    LaunchedEffect(isFlipping) {
        if (isFlipping) {
            // 무한 애니메이션
            rotation.animateTo(
                targetValue = 180f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        2000,
                        easing = EaseInOutCubic
                    ),
                    repeatMode = RepeatMode.Reverse
                ),
            )
        } else {
            // 애니메이션으로 앞면 보이게 뒤집고 종료
            rotation.animateTo(
                targetValue = 0f,
                animationSpec = tween(
                    2000,
                    easing = EaseOutExpo
                ),
            )
        }
    }

    AsyncImage(
        model = if (rotation.value < 90f) frontImage else backImage,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .graphicsLayer {
                rotationY = rotation.value
                cameraDistance = 12f * density
            }
            .width(width)
            .aspectRatio(3 / 5f)
            .clip(RoundedCornerShape(15.dp))
    )
}

@Preview
@Composable
private fun FlippableCardPreview() {
    var isFlipping by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        FlippableCard(
            isFlipping = isFlipping,
        )
    }
}