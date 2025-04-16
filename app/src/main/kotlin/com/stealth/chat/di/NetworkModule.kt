package com.stealth.chat.di

import com.stealth.chat.data.local.TokenManager
import com.stealth.chat.data.remote.*
import com.stealth.chat.network.ChatWebSocketListener
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "http://homelab.chestnut-snake.ts.net:3000"

    @Provides
    @Singleton
    fun provideTokenProvider(tokenManager: TokenManager): TokenProvider =
        DataStoreTokenProvider(tokenManager)

    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenProvider: TokenProvider): AuthInterceptor =
        AuthInterceptor(tokenProvider)

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideWebSocketListener(): ChatWebSocketListener {
        return ChatWebSocketListener()
    }

    @Provides
    @Singleton
    fun provideWebSocket(
        client: OkHttpClient,
        tokenProvider: TokenProvider,
        listener: ChatWebSocketListener
    ): WebSocket {
        val request = Request.Builder()
            .url("wss://yourserver.com/chat") // Replace this with real WS URL
            .addHeader("Authorization", "Bearer ${tokenProvider.getToken()}")
            .build()

        return client.newWebSocket(request, listener)
    }
}
