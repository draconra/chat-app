package com.stealth.chat.data.remote

import com.stealth.chat.data.local.TokenManager
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreTokenProvider @Inject constructor(
    private val tokenManager: TokenManager
) : TokenProvider {
    override fun getToken(): String {
        return runBlocking {
            tokenManager.getAccessToken() ?: ""
        }
    }
}
