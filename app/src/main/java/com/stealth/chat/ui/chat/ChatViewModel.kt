package com.stealth.chat.ui.chat

import androidx.lifecycle.ViewModel
import com.stealth.chat.model.Chat
import com.stealth.chat.model.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ChatViewModel : ViewModel() {

    private val _chat = MutableStateFlow<Chat?>(null)
    val chat: StateFlow<Chat?> = _chat

    fun setChatInfo(chat: Chat) {
        // Simulate initial messages directly into Chat object
        val chatWithMessages = chat.copy(
            message = listOf(
                Message(1, "Hi from chat ${chat.id}", isSentByMe = false),
                Message(2, "Hello!", isSentByMe = true)
            )
        )
        _chat.value = chatWithMessages
    }

    fun sendMessage(text: String) {
        if (text.isNotBlank()) {
            val currentChat = _chat.value
            if (currentChat != null) {
                val newMessages = currentChat.message + Message(
                    id = currentChat.message.size + 1,
                    text = text,
                    isSentByMe = true
                )
                _chat.value = currentChat.copy(message = newMessages)

                autoReply()
            }
        }
    }

    private fun autoReply() {
        val currentChat = _chat.value
        if (currentChat != null) {
            val newMessages = currentChat.message + Message(
                id = currentChat.message.size + 1,
                text = "This is an auto-reply!",
                isSentByMe = false
            )
            _chat.value = currentChat.copy(message = newMessages)
        }
    }
}
