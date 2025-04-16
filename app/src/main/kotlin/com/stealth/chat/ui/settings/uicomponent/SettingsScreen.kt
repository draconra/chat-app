@file:OptIn(ExperimentalMaterial3Api::class)

package com.stealth.chat.ui.settings.uicomponent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.google.firebase.BuildConfig

@Composable
fun SettingsScreen(
    baseUrl: String,
    username: String,
    onEditBaseUrl: () -> Unit,
    onEditUsername: () -> Unit
) {
    val versionName = BuildConfig.VERSION_NAME
    val buildNumber = "14"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                )
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
            Text("Version: $versionName (Build $buildNumber)", style = MaterialTheme.typography.bodyMedium)

            Spacer(Modifier.height(16.dp))
            SettingItem(Icons.Default.Build, "Change Base URL: $baseUrl", onEditBaseUrl)
            SettingItem(Icons.Default.Person, "Change Username: $username", onEditUsername)
        }
    }
}

@Composable
fun SettingItem(icon: ImageVector, title: String, onClick: () -> Unit) {
    ListItem(
        leadingContent = { Icon(icon, contentDescription = null) },
        headlineContent = { Text(title) },
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    )
    Divider()
}