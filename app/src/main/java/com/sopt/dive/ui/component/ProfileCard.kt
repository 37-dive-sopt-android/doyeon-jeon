package com.sopt.dive.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.sopt.dive.data.model.Profile
import com.sopt.dive.ui.theme.Black1
import com.sopt.dive.ui.theme.Black2
import com.sopt.dive.ui.theme.Red
import com.sopt.dive.ui.theme.Typography

@Composable
fun ProfileCard(
    profile: Profile,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
            ) {
                AsyncImage(
                    model = profile.image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .align(Alignment.Center)
                )
                if (profile.isBirthday) {
                    Text(
                        "🎂",
                        modifier = Modifier.align(Alignment.BottomEnd)
                    )
                }
            }
            Spacer(Modifier.width(8.dp))
            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 12.dp)
            ) {
                Text(
                    profile.name,
                    style = Typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Black1,
                )
                if (profile.isUpdated) {
                    Text(
                        "•",
                        style = Typography.titleMedium,
                        color = Red,
                        modifier = Modifier
                            .padding(start = 2.dp)
                            .align(Alignment.Top)
                    )
                }
            }
            profile.music?.let {
                DiveChip(
                    content = "🎵 ${it.title}",
                    style = DiveChipStyle.GreenStyle,
                    modifier = Modifier.widthIn(max = 120.dp)
                )
            }
        }
        profile.bio?.let {
            Spacer(Modifier.height(4.dp))
            Text(
                it,
                style = Typography.titleSmall,
                color = Black2
            )
        }
    }
}

//@Preview
//@Composable
//private fun ProfileCardPreviewDefault() {
//    ProfileCard(
//        profile = Profile(
//            image = R.drawable.chopper,
//            name = "도도",
//            bio = "도도도도도도돋도",
//        ),
//    )
//}
//
//@Preview
//@Composable
//private fun ProfileCardPreviewBirthday() {
//    ProfileCard(
//        profile = Profile(
//            image = R.drawable.chopper,
//            name = "도도",
//            isBirthday = true
//        ),
//    )
//}
//
//@Preview
//@Composable
//private fun ProfileCardUpdated() {
//    ProfileCard(
//        profile = Profile(
//            image = R.drawable.chopper,
//            name = "도도",
//            bio = "도도도도도도돋도",
//            isUpdated = true
//        ),
//    )
//}
//
//@Preview
//@Composable
//private fun ProfileCardMusic() {
//    ProfileCard(
//        profile = Profile(
//            image = R.drawable.chopper,
//            name = "도도",
//            bio = "도도도도도도돋도",
//            music = ProfileMusic(
//                id = 1,
//                title = "LINK IT ALL",
//                singer = "SPYAIR"
//            )
//        ),
//    )
//}
//
//@Preview
//@Composable
//private fun ProfileCardAll() {
//    ProfileCard(
//        profile = Profile(
//            image = R.drawable.chopper,
//            name = "도도",
//            bio = "도도도도도도돋도",
//            isBirthday = true,
//            isUpdated = true,
//            music = ProfileMusic(
//                id = 1,
//                title = "LINK IT ALL",
//                singer = "SPYAIR"
//            )
//        ),
//    )
//}
//
//@Preview
//@Composable
//private fun ProfileCardHeavy() {
//    ProfileCard(
//        profile = Profile(
//            image = R.drawable.chopper,
//            name = "도도도도도도도도도도도도도도도도",
//            bio = "도도도도도도돋도도도도도도도돋도도도도도도도돋도도도도도도도돋도도도도도도도돋도도도도도도도돋도",
//            isBirthday = true,
//            isUpdated = true,
//            music = ProfileMusic(
//                id = 1,
//                title = "LINK IT ALL LINK IT ALL LINK IT ALL LINK IT ALL LINK IT ALL LINK IT ALL LINK IT ALL LINK IT ALL LINK IT ALL LINK IT ALL LINK IT ALL LINK IT ALL LINK IT ALL LINK IT ALL",
//                singer = "SPYAIR"
//            )
//        ),
//    )
//}