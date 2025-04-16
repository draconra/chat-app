package com.stealth.chat.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PinPreferenceManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        private val PIN_VERIFIED_KEY = booleanPreferencesKey("pin_verified")
    }

    val isPinVerified: Flow<Boolean> = dataStore.data.map { prefs ->
        prefs[PIN_VERIFIED_KEY] ?: false
    }

    suspend fun savePinVerified() {
        dataStore.edit { prefs ->
            prefs[PIN_VERIFIED_KEY] = true
        }
    }
}
