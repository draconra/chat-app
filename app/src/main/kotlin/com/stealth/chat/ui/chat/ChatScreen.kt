package com.stealth.chat.ui.chat

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.stealth.chat.model.Chat
import com.stealth.chat.model.Message
import com.stealth.chat.ui.chat.uicomponent.ChatBubble
import com.stealth.chat.ui.chat.uicomponent.ChatTopBar
import com.stealth.chat.ui.chat.uicomponent.MessageInput

@Composable
fun ChatScreenContent(
    chat: Chat,
    onSend: (String) -> Unit,
    onAttachImage: () -> Unit
) {
    Scaffold(
        contentWindowInsets = WindowInsets.systemBars,
        topBar = {
            ChatTopBar(chatName = chat.name, avatarUrl = chat.avatarUrl)
        },
        bottomBar = {
            MessageInput(
                onSend = onSend,
                onAttachImage = onAttachImage
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(
                    bottom = WindowInsets.ime.asPaddingValues().calculateBottomPadding()
                ),
            reverseLayout = false
        ) {
            items(chat.message) { message ->
                ChatBubble(message)
            }
        }
    }
}

@Preview
@Composable
fun ChatScreenPreview() {
    ChatScreenContent(
        chat = Chat(
            id = 1,
            name = "Preview User",
            avatarUrl = "",
            lastMessage = "",
            timestamp = "",
            isUnread = true,
            message = listOf(
                Message(1, "Hello!", isSentByMe = true, createdAt = ""),
                Message(2, "Hi there!", isSentByMe = false, createdAt = "")
            )
        ),
        onSend = { _ -> },
        onAttachImage = {}
    )
}
