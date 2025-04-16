package com.stealth.chat.data.remote

import com.stealth.chat.data.local.SettingsPreferenceManager
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreBaseUrlProvider @Inject constructor(
    private val settingsPrefs: SettingsPreferenceManager
) : BaseUrlProvider {
    override suspend fun getBaseUrl(): String {
        return settingsPrefs.baseUrl.first()
    }
}
