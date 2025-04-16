package com.stealth.chat.di

import android.content.Context
import com.stealth.chat.data.local.SettingsPreferenceManager
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
}
