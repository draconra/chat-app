package com.stealth.chat.ui.chat

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stealth.chat.data.remote.ApiService
import com.stealth.chat.model.Chat
import com.stealth.chat.model.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    private val _chat = MutableStateFlow<Chat?>(null)
    val chat: StateFlow<Chat?> = _chat

    fun setChatInfo(chat: Chat) {
        _chat.value = chat
        fetchChatHistory(chat.id)
    }

    @SuppressLint("NewApi")
    private fun fetchChatHistory(userId: Int) {
        viewModelScope.launch {
            try {
                val response = apiService.getMessageHistory(userId)
                if (response.isSuccessful) {
                    val messages = response.body()?.data?.messages?.map {
                        Message(
                            id = it.id,
                            text = it.content,
                            isSentByMe = it.receiverId == userId,
                            disappearAfterMillis = null,
                            createdAt = it.createdAt
                        )
                    }?.sortedBy {
                        Instant.parse(it.createdAt)
                    } ?: emptyList()

                    _chat.value = _chat.value?.copy(message = messages)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun sendImage(imageUri: String) {
//        val currentChat = _chat.value
//        if (currentChat != null) {
//            val newMessages = currentChat.message + Message(
//                id = currentChat.message.size + 1,
//                text = "",
//                isSentByMe = true,
//                createdAt = it.createdAt
//                imageUrl = imageUri
//            )
//            _chat.value = currentChat.copy(message = newMessages)
//        }
    }

    fun sendMessage(text: String) {
        val currentChat = _chat.value ?: return
        if (text.isBlank()) return

        viewModelScope.launch {
            try {
                val response = apiService.sendMessage(
                    com.stealth.chat.data.remote.MessageRequest(
                        content = text,
                        receiverId = currentChat.id
                    )
                )
                if (response.isSuccessful) {
                    fetchChatHistory(currentChat.id)
                } else {
                    // Handle error, optionally emit error state
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}
