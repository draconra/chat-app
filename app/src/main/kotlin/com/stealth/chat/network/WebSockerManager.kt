package com.stealth.chat.network

import android.util.Log
import com.stealth.chat.data.remote.TokenProvider
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import javax.inject.Inject

class WebSocketManager @Inject constructor(
    private val client: OkHttpClient,
    private val tokenProvider: TokenProvider,
    private val listener: ChatWebSocketListener
) {
    private var webSocket: WebSocket? = null

    fun connect(baseUrl: String) {
        val wsUrl = baseUrl.replace("https", "wss") + "/api/chat/ws"
        val token = tokenProvider.getToken()

        Log.d("WebSocketManager", "Connecting to $wsUrl with token: $token")

        val request = Request.Builder()
            .url(wsUrl)
            .addHeader("Authorization", "Bearer $token")
            .build()

        webSocket = client.newWebSocket(request, listener)
    }

    fun disconnect() {
        webSocket?.close(1000, "Disconnected by user")
        webSocket = null
    }
}
