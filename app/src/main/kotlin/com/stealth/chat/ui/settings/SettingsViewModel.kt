package com.stealth.chat.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stealth.chat.data.local.SettingsPreferenceManager
import com.stealth.chat.data.local.TokenManager
import com.stealth.chat.data.remote.ApiService
import com.stealth.chat.data.remote.LoginRequest
import com.stealth.chat.network.WebSocketManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsPrefs: SettingsPreferenceManager,
    private val apiService: ApiService,
    private val tokenManager: TokenManager,
    private val webSocketManager: WebSocketManager
) : ViewModel() {

    private val _snackbarMessage = MutableStateFlow<String?>(null)
    val snackbarMessage: StateFlow<String?> = _snackbarMessage

    val baseUrl = settingsPrefs.baseUrl.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        "https://2ab8-182-253-57-146.ngrok-free.app"
    )

    val username = settingsPrefs.username.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        "user@backdoor.secure"
    )

    fun saveBaseUrl(newUrl: String) {
        viewModelScope.launch {
            settingsPrefs.setBaseUrl(newUrl)

            // reconnect WebSocket with updated baseUrl
            webSocketManager.connect(newUrl)
        }
    }

    fun saveUsername(newUsername: String) {
        viewModelScope.launch {
            settingsPrefs.setUsername(newUsername)

            // Optional: login user right away and connect
            val result = apiService.login(LoginRequest(email = newUsername, pin = "123456"))
            if (result.isSuccessful) {
                result.body()?.let {
                    tokenManager.saveTokens(it.access_token, it.refresh_token)
                    val currentBaseUrl = settingsPrefs.baseUrl.first()
                    webSocketManager.connect(currentBaseUrl)
                }
            }
        }
    }

    fun connect() {
        viewModelScope.launch {
            val currentBaseUrl = settingsPrefs.baseUrl.first()
            val currentUsername = settingsPrefs.username.first()

            val result = apiService.login(LoginRequest(email = currentUsername, pin = "123456"))
            if (result.isSuccessful) {
                result.body()?.let {
                    tokenManager.saveTokens(it.access_token, it.refresh_token)
                    webSocketManager.connect(currentBaseUrl)
                    _snackbarMessage.value = "Connected to server ✅"
                }
            } else {
                _snackbarMessage.value = "Failed to connect ❌"
            }
        }
    }

    fun clearSnackbar() {
        _snackbarMessage.value = null
    }
}

