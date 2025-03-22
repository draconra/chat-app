package com.stealth.chat.ui.chat.list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stealth.chat.model.Chat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDateTime

class ChatListViewModel : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    private val _chats = MutableStateFlow(
        listOf(
            Chat(
                id = 1,
                name = "Alice",
                lastMessage = "Hey, how are you?",
                avatarUrl = "https://randomuser.me/api/portraits/women/1.jpg",
                timestamp ="2023-09-15T10:30:00",
                isUnread = true

            ),
            Chat(
                id = 2,
                name = "Bob",
                lastMessage = "See you tomorrow!",
                avatarUrl = "https://randomuser.me/api/portraits/men/2.jpg",
                timestamp = "",
                isUnread = false
            ),
            Chat(
                id = 3,
                name = "Charlie",
                lastMessage = "Thanks for your help!",
                avatarUrl = "https://randomuser.me/api/portraits/men/3.jpg",
                timestamp = "",
                isUnread = false
            )
        )
    )
    @RequiresApi(Build.VERSION_CODES.O)
    val chats = _chats.asStateFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    val filteredChats = combine(_chats, _searchQuery) { chats, query ->
        if (query.isBlank()) chats
        else chats.filter {
            it.name.contains(query, ignoreCase = true) ||
                    it.lastMessage.contains(query, ignoreCase = true)
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, _chats.value)

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    private val _isSearchActive = MutableStateFlow(false)
    val isSearchActive = _isSearchActive.asStateFlow()

    fun toggleSearchBar() {
        _isSearchActive.value = !_isSearchActive.value
    }

    fun clearSearch() {
        _searchQuery.value = ""
        _isSearchActive.value = false
    }
}
