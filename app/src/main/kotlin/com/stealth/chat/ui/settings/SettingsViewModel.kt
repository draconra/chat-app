package com.stealth.chat.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stealth.chat.data.local.SettingsPreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsPrefs: SettingsPreferenceManager
) : ViewModel() {

    val baseUrl = settingsPrefs.baseUrl.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        "https://default.url"
    )

    val username = settingsPrefs.username.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        "admin"
    )

    fun saveBaseUrl(newUrl: String) {
        viewModelScope.launch {
            settingsPrefs.setBaseUrl(newUrl)
        }
    }

    fun saveUsername(newUsername: String) {
        viewModelScope.launch {
            settingsPrefs.setUsername(newUsername)
        }
    }
}
