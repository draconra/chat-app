package com.stealth.chat.ui.chat

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.stealth.chat.model.Chat
import com.stealth.chat.ui.theme.ChatAppTheme

class ChatActivity : ComponentActivity() {

    private val chatViewModel: ChatViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val chatJson = intent.getStringExtra("chatData")
        val chat: Chat? = Gson().fromJson(chatJson, Chat::class.java)

        // Send to ViewModel
        chat?.let {
            chatViewModel.setChatInfo(it)
        }

        setContent {
            ChatAppTheme {
                ChatScreen(chatViewModel)
            }
        }
    }
}