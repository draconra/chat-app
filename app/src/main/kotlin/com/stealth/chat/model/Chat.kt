package com.stealth.chat.model

data class Chat(
    val id: Int,
    val name: String,
    val lastMessage: String = "",
    val avatarUrl: String,
    val timestamp: String = "",
    val isUnread: Boolean = false,
    val message: List<Message> = emptyList()
)
