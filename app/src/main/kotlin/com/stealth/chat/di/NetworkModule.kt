package com.stealth.chat.di

import com.stealth.chat.data.local.SettingsPreferenceManager
import com.stealth.chat.data.local.TokenManager
import com.stealth.chat.data.remote.*
import com.stealth.chat.network.ChatWebSocketListener
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideTokenProvider(tokenManager: TokenManager): TokenProvider =
        DataStoreTokenProvider(tokenManager)

    @Provides
    @Singleton
    fun provideBaseUrlProvider(settingsPrefs: SettingsPreferenceManager): BaseUrlProvider =
        DataStoreBaseUrlProvider(settingsPrefs)

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
    fun provideRetrofit(
        client: OkHttpClient,
        baseUrlProvider: BaseUrlProvider
    ): Retrofit {
        val baseUrl = kotlinx.coroutines.runBlocking { baseUrlProvider.getBaseUrl() }
        return Retrofit.Builder()
            .baseUrl(baseUrl)
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
        listener: ChatWebSocketListener,
        baseUrlProvider: BaseUrlProvider
    ): WebSocket {
        val wsUrl = runBlocking {
            baseUrlProvider.getBaseUrl().replace("https", "wss") + "/api/chat/ws"
        }

        val request = Request.Builder()
            .url(wsUrl)
            .addHeader("Authorization", "Bearer ${tokenProvider.getToken()}")
            .build()

        return client.newWebSocket(request, listener)
    }
}

