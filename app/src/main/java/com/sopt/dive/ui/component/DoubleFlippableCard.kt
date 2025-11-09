package com.sopt.dive.ui.component

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil3.compose.AsyncImage
import com.sopt.dive.ui.theme.Black1
import com.sopt.dive.ui.theme.Coral
import com.sopt.dive.ui.theme.Typography
import com.sopt.dive.ui.theme.White

@Composable
fun DoubleFlippableCard(
    modifier: Modifier = Modifier,
    image: String = "https://i.pinimg.com/736x/56/12/97/561297f7e47df59a250c851382d0fab5.jpg",
    text: String = "글자".repeat(200),
) {
    var showImage by remember { mutableStateOf(true) }
    val transition = updateTransition(showImage, label = "transition")

    val imageShape = RoundedCornerShape(
        topEnd = 100.dp,
        bottomStart = 100.dp,
        topStart = 20.dp,
        bottomEnd = 20.dp
    )

    val textBoxShape = RoundedCornerShape(
        topEnd = 20.dp,
        bottomStart = 20.dp,
        topStart = 100.dp,
        bottomEnd = 100.dp
    )

    // 이미지 회전
    val rotation by transition.animateFloat(
        label = "rotation",
        transitionSpec = {
            spring(
                dampingRatio = 0.8f,
                stiffness = 177.8f
            )
        }
    ) {
        when (it) {
            true -> 0f
            false -> 180f
        }
    }

    // 글자 투명도 (fadeIn/Out 효과)
    val alpha by transition.animateFloat(
        label = "alpha",
        transitionSpec = {
            tween(durationMillis = 1000)
        }
    ) {
        when (it) {
            true -> 0f
            false -> 1f
        }
    }

    // 이미지 위치
    val offset by transition.animateDp {
        when (it) {
            true -> 0.dp
            false -> 20.dp
        }
    }

    Box(
        modifier = modifier.padding(20.dp),
    ) {
        // 이미지
        AsyncImage(
            model = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(height = 400.dp, width = 300.dp)
                .zIndex(if (rotation < 90) 1f else -1f)
                .offset(x = offset, y = offset)
                .shadow(
                    elevation = if (rotation < 30) 4.dp else 0.dp,
                    spotColor = Black1,
                    shape = imageShape,
                )
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    showImage = !showImage
                }
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 12f * density
                }
                .clip(imageShape)
        )
        // 글자
        Box(
            modifier = Modifier
                .size(height = 390.dp, width = 290.dp)
                .zIndex(0f)
                .shadow(
                    elevation = if (showImage) 0.dp else 4.dp,
                    spotColor = Black1,
                    shape = textBoxShape,
                )
                .clip(textBoxShape)
                .background(Coral)
        ) {
            Text(
                text,
                style = Typography.bodyLarge,
                color = White.copy(alpha = alpha)
            )
        }
    }
}

@Preview
@Composable
private fun DoubleFlippableCardPreview() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        DoubleFlippableCard()
    }
}