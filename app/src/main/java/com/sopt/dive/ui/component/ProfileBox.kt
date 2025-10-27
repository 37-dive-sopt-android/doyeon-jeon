package com.sopt.dive.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.dive.R
import com.sopt.dive.ui.theme.Black1
import com.sopt.dive.ui.theme.Black2
import com.sopt.dive.ui.theme.Typography

@Composable
fun ProfileBox(
    @DrawableRes image: Int,
    name: String,
    bio: String,
    modifier: Modifier= Modifier
) {
    Column (
        modifier = modifier
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                name,
                style = Typography.titleMedium,
                color = Black1
            )
        }
        Spacer(Modifier.height(4.dp))
        Text(
            bio,
            style = Typography.titleSmall,
            color = Black2
        )
    }
}

@Preview
@Composable
fun ProfileBoxPreview() {
    ProfileBox(
        image = R.drawable.chopper,
        name = "도도",
        bio = "도도도도도도돋도",
    )
}