package com.stealth.chat.ui.chat

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.google.gson.Gson
import com.stealth.chat.model.Chat
import com.stealth.chat.ui.theme.ChatAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatDetailActivity : ComponentActivity() {

    private val chatViewModel: ChatViewModel by viewModels()
    private lateinit var imagePickerLauncher: ActivityResultLauncher<String>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val chatJson = intent.getStringExtra("chatData")
        val chat: Chat? = Gson().fromJson(chatJson, Chat::class.java)

        // Send to ViewModel
        chat?.let {
            chatViewModel.setChatInfo(it)
        }

        // Register image picker
        imagePickerLauncher =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                uri?.let {
                    chatViewModel.sendImage(it.toString())
                }
            }

        setContent {
            ChatAppTheme {
                val chatState by chatViewModel.chat.collectAsState()

                chatState?.let { chat ->
                    ChatScreenContent(
                        chat = chat,
                        onSend = chatViewModel::sendMessage,
                        onAttachImage = {
                            imagePickerLauncher.launch("image/*")
                        }
                    )
                }
            }
        }
    }
}
