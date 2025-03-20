package com.stealth.chat.ui.chat.list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.stealth.chat.model.Chat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime

class ChatListViewModel : ViewModel() {
    @RequiresApi(Build.VERSION_CODES.O)
    private val _chats = MutableStateFlow(
        listOf(
            Chat(
                id = 1,
                name = "Alice",
                lastMessage = "Hey, how are you?",
                avatarUrl = "https://randomuser.me/api/portraits/women/1.jpg",
                timestamp = LocalDateTime.now().minusMinutes(5),
                isUnread = true

            ),
            Chat(
                id = 2,
                name = "Bob",
                lastMessage = "See you tomorrow!",
                avatarUrl = "https://randomuser.me/api/portraits/men/2.jpg",
                timestamp = LocalDateTime.now().minusHours(1),
                isUnread = false
            ),
            Chat(
                id = 3,
                name = "Charlie",
                lastMessage = "Thanks for your help!",
                avatarUrl = "https://randomuser.me/api/portraits/men/3.jpg",
                timestamp = LocalDateTime.now().minusDays(1),
                isUnread = false
            )
        )
    )
    @RequiresApi(Build.VERSION_CODES.O)
    val chats = _chats.asStateFlow()
}
