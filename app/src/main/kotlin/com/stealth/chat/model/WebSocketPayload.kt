package com.stealth.chat.model

import com.stealth.chat.data.remote.MessageResponse

data class WebSocketPayload(
    val type: String,
    val data: MessageResponse
)