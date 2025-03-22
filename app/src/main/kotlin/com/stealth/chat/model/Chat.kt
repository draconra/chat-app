package com.stealth.chat.model

import java.time.LocalDateTime

data class Chat(
    val id: Int,
    val name: String,
    val lastMessage: String,
    val avatarUrl: String,
    val timestamp: String,
    val isUnread: Boolean,
    val message: List<Message> = emptyList()
)
