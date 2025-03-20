package com.stealth.chat.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stealth.chat.model.Chat
import com.stealth.chat.model.Message
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    private val _chat = MutableStateFlow<Chat?>(null)
    val chat: StateFlow<Chat?> = _chat

    fun setChatInfo(chat: Chat) {
        val chatWithMessages = chat.copy(
            message = listOf(
                Message(1, "Hi from chat ${chat.id}", isSentByMe = false),
                Message(2, "Hello!", isSentByMe = true)
            )
        )
        _chat.value = chatWithMessages
    }

    fun sendImage(imageUri: String) {
        val currentChat = _chat.value
        if (currentChat != null) {
            val newMessages = currentChat.message + Message(
                id = currentChat.message.size + 1,
                text = "",
                isSentByMe = true,
                imageUrl = imageUri
            )
            _chat.value = currentChat.copy(message = newMessages)
        }
    }

    fun sendMessage(text: String, disappearAfterMillis: Long? = null) {
        if (text.isNotBlank()) {
            val currentChat = _chat.value
            if (currentChat != null) {
                val newMsg = Message(
                    id = currentChat.message.size + 1,
                    text = text,
                    isSentByMe = true,
                    disappearAfterMillis = disappearAfterMillis
                )
                val newMessages = currentChat.message + newMsg
                _chat.value = currentChat.copy(message = newMessages)

                disappearAfterMillis?.let {
                    startDisappearTimer(newMsg.id, it)
                }

                autoReply()
            }
        }
    }

    private fun startDisappearTimer(messageId: Int, duration: Long) {
        viewModelScope.launch {
            delay(duration)
            removeMessage(messageId)
        }
    }

    private fun removeMessage(messageId: Int) {
        val currentChat = _chat.value ?: return
        val updatedMessages = currentChat.message.filterNot { it.id == messageId }
        _chat.value = currentChat.copy(message = updatedMessages)
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
