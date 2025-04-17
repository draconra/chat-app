package com.stealth.chat.ui.chat

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.stealth.chat.data.local.SettingsPreferenceManager
import com.stealth.chat.data.remote.ApiService
import com.stealth.chat.data.remote.MessageRequest
import com.stealth.chat.data.remote.MessageResponse
import com.stealth.chat.model.Chat
import com.stealth.chat.model.Message
import com.stealth.chat.network.ChatWebSocketListener
import com.stealth.chat.network.WebSocketManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

@SuppressLint("NewApi")
@HiltViewModel
class ChatViewModel @Inject constructor(
    private val settingsPrefs: SettingsPreferenceManager,
    private val apiService: ApiService,
    private val webSocketManager: WebSocketManager,
    private val webSocketListener: ChatWebSocketListener
) : ViewModel() {

    private val _chat = MutableStateFlow<Chat?>(null)
    val chat: StateFlow<Chat?> = _chat

    init {
        viewModelScope.launch {
            val currentBaseUrl = settingsPrefs.baseUrl.first()
            webSocketManager.connect(currentBaseUrl)
        }
    }

    fun setChatInfo(chat: Chat) {
        _chat.value = chat
        fetchChatHistory(chat.id)
        listenToIncomingMessages(chat.id)
    }

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
                    MessageRequest(
                        content = text,
                        receiverId = currentChat.id
                    )
                )

                if (response.isSuccessful) {
                    fetchChatHistory(currentChat.id)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun listenToIncomingMessages(opponentUserId: Int) {
        val userChannel = webSocketListener.getChannelForUser(opponentUserId)

        viewModelScope.launch {
            for (json in userChannel) {
                try {
                    val incoming = Gson().fromJson(json, MessageResponse::class.java)

                    val currentChat = _chat.value ?: continue
                    val opponentId = currentChat.id

                    val newMessage = Message(
                        id = incoming.id,
                        text = incoming.content,
                        isSentByMe = incoming.senderId != opponentId,
                        createdAt = incoming.createdAt
                    )

                    val exists = currentChat.message.any { it.id == incoming.id }
                    if (exists) continue

                    val updatedMessages = currentChat.message + newMessage
                    _chat.value = currentChat.copy(message = updatedMessages.sortedBy {
                        Instant.parse(it.createdAt)
                    })
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
