package com.stealth.chat.ui.chat

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stealth.chat.model.Message
import com.stealth.chat.ui.chat.uicomponent.ChatBubble
import com.stealth.chat.ui.chat.uicomponent.ChatTopBar
import com.stealth.chat.ui.chat.uicomponent.MessageInput

@Composable
fun ChatScreen(viewModel: ChatViewModel) {
    val chat by viewModel.chat.collectAsState()

    Scaffold(
        topBar = {
            chat?.let {
                ChatTopBar(chatName = it.name, avatarUrl = it.avatarUrl)
            }
        },
        bottomBar = {
            MessageInput(onSend = viewModel::sendMessage)
        }
    ) { innerPadding ->
        // Message list body
        chat?.let {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                reverseLayout = true
            ) {
                items(it.message.reversed()) { message ->
                    ChatBubble(message)
                }
            }
        }
    }
}

@Preview
@Composable
fun ChatScreenPreview() {
    ChatScreen(viewModel = ChatViewModel())
}
