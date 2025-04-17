package com.stealth.chat.network

import android.util.Log
import com.google.gson.Gson
import com.stealth.chat.data.remote.MessageResponse
import com.stealth.chat.model.WebSocketPayload
import kotlinx.coroutines.channels.Channel
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class ChatWebSocketListener : WebSocketListener() {

    private val gson = Gson()

    // 🔁 Dynamic channels keyed by userId
    private val messageChannels: MutableMap<Int, Channel<String>> = mutableMapOf()

    fun getChannelForUser(userId: Int): Channel<String> {
        return messageChannels.getOrPut(userId) { Channel(Channel.UNLIMITED) }
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        Log.d("WebSocket", "Connected")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        Log.d("WebSocket", "Received: $text")

        try {
            val payload = gson.fromJson(text, WebSocketPayload::class.java)

            if (payload.type == "new_message") {
                val senderId = payload.data.senderId
                val channel = getChannelForUser(senderId) // ✅ route by sender
                channel.trySend(gson.toJson(payload.data))
            }

        } catch (e: Exception) {
            Log.e("WebSocket", "Message parse failed: ${e.message}")
        }
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        Log.e("WebSocket", "Error: ${t.message}")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        webSocket.close(code, reason)
        Log.d("WebSocket", "Closing: $reason")
    }
}