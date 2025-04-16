package com.stealth.chat.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.settingsDataStore by preferencesDataStore(name = "settings_prefs")

class SettingsPreferenceManager @Inject constructor(
    private val context: Context
) {
    companion object {
        private val BASE_URL_KEY = stringPreferencesKey("base_url")
        private val USERNAME_KEY = stringPreferencesKey("username")
    }

    val baseUrl: Flow<String> =
        context.settingsDataStore.data.map { it[BASE_URL_KEY] ?: "https://default.url" }
    val username: Flow<String> = context.settingsDataStore.data.map { it[USERNAME_KEY] ?: "admin" }

    suspend fun setBaseUrl(value: String) {
        context.settingsDataStore.edit { prefs ->
            prefs[BASE_URL_KEY] = value
        }
    }

    suspend fun setUsername(value: String) {
        context.settingsDataStore.edit { prefs ->
            prefs[USERNAME_KEY] = value
        }
    }
}
