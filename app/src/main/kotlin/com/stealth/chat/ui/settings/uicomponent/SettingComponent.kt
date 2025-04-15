package com.stealth.chat.ui.settings.uicomponent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Settings", style = MaterialTheme.typography.titleLarge)

        Spacer(Modifier.height(16.dp))
        SettingItem("Change Base URL") { /* open dialog or navigate */ }
        SettingItem("About") { /* show info */ }
        SettingItem("Version App") {

        }
    }
}

@Composable
fun SettingItem(title: String, onClick: () -> Unit) {
    ListItem(
        headlineContent = { Text(title) },
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    )
    Divider()
}

@Composable
fun SettingsHost() {
    var isVerified by remember { mutableStateOf(false) }

    if (isVerified) {
        SettingsScreen()
    } else {
        PinChallengeScreen(onPinVerified = { isVerified = true })
    }
}