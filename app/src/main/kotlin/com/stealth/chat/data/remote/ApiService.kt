package com.stealth.chat.data.remote

import retrofit2.http.*
import retrofit2.Response

data class LoginRequest(val email: String, val pin: String)
data class LoginResponse(val access_token: String, val refresh_token: String)

data class MessageRequest(val content: String, val receiver_id: Int)
data class MessageResponse(
    val id: Int,
    val content: String,
    val sender_id: Int,
    val receiver_id: Int
)

interface ApiService {

    @POST("/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("/auth/refresh")
    suspend fun refresh(): Response<LoginResponse>

    @GET("/api/chat/messages")
    suspend fun getMessages(): Response<List<MessageResponse>>

    @POST("/api/chat/messages")
    suspend fun sendMessage(@Body request: MessageRequest): Response<Unit>
}
