package com.stealth.chat.model

data class Message(
    val id: Int,
    val text: String,
    val isSentByMe: Boolean,
    val imageUrl: String? = null
)