package com.stealth.chat.model

data class MessageAudio(
    val uri: String,
    val duration: Long,
    val timestamp: Long = System.currentTimeMillis()
)