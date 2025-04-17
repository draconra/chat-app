package com.stealth.chat.ui.chat.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stealth.chat.data.remote.ApiService
import com.stealth.chat.model.Chat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _chats = MutableStateFlow<List<Chat>>(emptyList())

    val chats = _chats.asStateFlow()

    val filteredChats = combine(_chats, _searchQuery) { chats, query ->
        if (query.isBlank()) chats
        else chats.filter {
            it.name.contains(query, ignoreCase = true) ||
                    it.lastMessage.contains(query, ignoreCase = true)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _isSearchActive = MutableStateFlow(false)
    val isSearchActive = _isSearchActive.asStateFlow()

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun toggleSearchBar() {
        _isSearchActive.value = !_isSearchActive.value
    }

    fun clearSearch() {
        _searchQuery.value = ""
        _isSearchActive.value = false
    }

    init {
        fetchParticipants()
    }

    private fun fetchParticipants() {
        viewModelScope.launch {
            try {
                val response = apiService.getParticipants()
                if (response.isSuccessful) {
                    val participants = response.body()?.data?.participants.orEmpty()
                    _chats.value = participants.map {
                        Chat(
                            id = it.id,
                            name = it.name,
                            avatarUrl = it.photoUrl
                        )
                    }
                } else {
                    Log.e("ChatListViewModel", "API failed: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("ChatListViewModel", "Exception: ${e.localizedMessage}")
            }
        }
    }
}

