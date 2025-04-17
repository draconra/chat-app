package com.stealth.chat.data.remote

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.*

// --- Auth ---
data class LoginRequest(
    val email: String,
    val pin: String
)

data class LoginResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String
)

// --- Message ---
data class MessageRequest(
    val content: String,
    @SerializedName("receiver_id") val receiverId: Int
)

data class MessageResponse(
    val id: Int,
    val content: String,
    @SerializedName("sender_id") val senderId: Int,
    @SerializedName("receiver_id") val receiverId: Int,
    @SerializedName("created_at") val createdAt: String
)

data class MessageHistoryResponse(
    val status: String,
    val data: MessageData
)

data class MessageData(
    val count: Int,
    val messages: List<MessageResponse>
)

// --- Chat Room ---
data class ChatRoomListResponse(
    val status: String,
    val data: ChatRoomData
)

data class ChatRoomData(
    val count: Int,
    val rooms: List<ChatRoom>
)

data class ChatRoom(
    val id: Int,
    val lastMessage: String,
    val unreadCount: Int,
    val updatedAt: String,
    val userId: Int,
    val userName: String
)

data class Participant(
    val id: Int,
    val name: String,
    @SerializedName("photo_url") val photoUrl: String
)

data class ParticipantData(
    val participants: List<Participant>,
    val count: Int
)

data class ParticipantResponse(
    val status: String,
    val data: ParticipantData
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

    @GET("/api/chat/messages/{userID}")
    suspend fun getMessageHistory(@Path("userID") userID: Int): Response<MessageHistoryResponse>

    @GET("/api/chat/participants")
    suspend fun getParticipants(): Response<ParticipantResponse>

    @GET("/api/chat/rooms")
    suspend fun getChatRooms(): Response<ChatRoomListResponse>
}
