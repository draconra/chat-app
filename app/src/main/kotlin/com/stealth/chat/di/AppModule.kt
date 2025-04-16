package com.stealth.chat.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.stealth.chat.data.local.PinPreferenceManager
import com.stealth.chat.data.local.SettingsPreferenceManager
import com.stealth.chat.util.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSettingsPreferenceManager(
        @ApplicationContext context: Context
    ): SettingsPreferenceManager {
        return SettingsPreferenceManager(context)
    }

    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = context.dataStore

    @Provides
    @Singleton
    fun providePinPreferenceManager(
        dataStore: DataStore<Preferences>
    ): PinPreferenceManager {
        return PinPreferenceManager(dataStore)
    }
}
