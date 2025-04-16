package com.stealth.chat.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.stealth.chat.MainActivity
import com.stealth.chat.ui.core.BaseActivity
import com.stealth.chat.ui.settings.uicomponent.PinChallengeScreen
import com.stealth.chat.ui.theme.ChatAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PinActivity : BaseActivity() {

    private val pinViewModel: PinViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ChatAppTheme {
                val isVerified by pinViewModel.isPinVerified.collectAsState()

                // If verified, go to MainActivity
                LaunchedEffect(isVerified) {
                    if (isVerified) {
                        startActivity(Intent(this@PinActivity, MainActivity::class.java))
                        finish()
                    }
                }

                // Otherwise, show PIN input screen
                if (!isVerified) {
                    PinChallengeScreen(
                        onPinVerified = {
                            pinViewModel.savePinVerified()
                        }
                    )
                }
            }
        }
    }
}