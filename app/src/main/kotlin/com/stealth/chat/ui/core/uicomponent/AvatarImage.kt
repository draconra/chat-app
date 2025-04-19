package com.stealth.chat.ui.core.uicomponent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.UserCircle

@Composable
fun AvatarImage(avatarUrl: String, size: Dp = 40.dp) {
    val modifier = Modifier
        .size(size)
        .clip(CircleShape)
        .background(Color.Gray)

    if (avatarUrl.isBlank()) {
        Icon(
            imageVector = FontAwesomeIcons.Solid.UserCircle,
            contentDescription = "Default Avatar",
            modifier = modifier,
            tint = Color.White
        )
    } else {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(avatarUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Avatar",
            modifier = modifier
        )
    }
}