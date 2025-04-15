package com.stealth.chat.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.stealth.chat.databinding.FragmentNotificationsBinding
import com.stealth.chat.ui.settings.uicomponent.SettingsHost
import com.stealth.chat.ui.theme.ChatAppTheme

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                ChatAppTheme {
                    SettingsHost()
                }
            }
        }
    }
}