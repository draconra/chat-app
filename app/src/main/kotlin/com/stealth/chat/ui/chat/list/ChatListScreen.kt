package com.stealth.chat.ui.chat.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.stealth.chat.model.Chat

@Composable
fun ChatListScreen(chats: List<Chat>, onChatClick: (Chat) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
    ) {
        items(chats) { chat ->
            ChatItem(chat, onChatClick)
        }
    }
}

@Composable
fun ChatItem(chat: Chat, onChatClick: (Chat) -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { onChatClick(chat) }
        .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically) {
        // Avatar
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(chat.avatarUrl).crossfade(true)
                .build(),
            contentDescription = "Avatar",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color.Gray)
        )

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            // Chat Name
            Text(
                text = chat.name, fontWeight = FontWeight.Bold, color = Color.Black
            )
            // Last Message
            Text(
                text = chat.lastMessage,
                color = if (chat.isUnread) Color.Black else Color.Gray,
                fontWeight = if (chat.isUnread) FontWeight.Bold else FontWeight.Normal
            )
        }

        // Timestamp & Unread Indicator
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "", color = Color.Gray
            )
            if (chat.isUnread) {
                Spacer(Modifier.size(5.dp))
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(Color.Red, CircleShape)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewChatListScreen() {
    ChatListScreen(chats = emptyList(), onChatClick = {})
}
