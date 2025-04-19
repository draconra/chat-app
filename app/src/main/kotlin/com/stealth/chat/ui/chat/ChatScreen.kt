package com.stealth.chat.ui.chat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.stealth.chat.model.Chat
import com.stealth.chat.model.Message
import com.stealth.chat.ui.chat.uicomponent.ChatBubble
import com.stealth.chat.ui.chat.uicomponent.ChatTopBar
import com.stealth.chat.ui.chat.uicomponent.MessageInput

@Composable
fun ChatScreen(
    chat: Chat,
    onSend: (String) -> Unit,
    onAttachImage: () -> Unit
) {
    val listState = rememberLazyListState()

    Scaffold(
        contentWindowInsets = WindowInsets.systemBars,
        topBar = { ChatTopBar(chat.name, chat.avatarUrl) },
        bottomBar = {
            Box(
                modifier = Modifier
                    .navigationBarsPadding()
                    .imePadding()
            ) {
                MessageInput(onSend = onSend, onAttachImage = onAttachImage)
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            state = listState,
            reverseLayout = false
        ) {
            items(chat.message) { message ->
                ChatBubble(message)
            }
        }
    }

    LaunchedEffect(chat.message.size) {
        if (chat.message.isNotEmpty()) {
            listState.animateScrollToItem(chat.message.lastIndex)
        }
    }
}

@Preview
@Composable
fun ChatScreenPreview() {
    ChatScreen(
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
