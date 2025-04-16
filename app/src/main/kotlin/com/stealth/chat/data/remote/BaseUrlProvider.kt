package com.stealth.chat.data.remote

interface BaseUrlProvider {
    suspend fun getBaseUrl(): String
}