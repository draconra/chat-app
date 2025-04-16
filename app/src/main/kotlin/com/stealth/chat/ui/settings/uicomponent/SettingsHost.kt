package com.stealth.chat.ui.settings.uicomponent

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.stealth.chat.ui.settings.SettingsViewModel

@Composable
fun SettingsHost(viewModel: SettingsViewModel = hiltViewModel()) {
    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarMessage by viewModel.snackbarMessage.collectAsState()
    var isVerified by remember { mutableStateOf(false) }
    var showBaseUrlSheet by remember { mutableStateOf(false) }
    var showUsernameSheet by remember { mutableStateOf(false) }

    if (snackbarMessage != null) {
        LaunchedEffect(snackbarMessage) {
            snackbarMessage?.let {
                snackbarHostState.showSnackbar(it)
                viewModel.clearSnackbar()
            }
        }
    }

    if (isVerified) {
        val baseUrl by viewModel.baseUrl.collectAsState()
        val username by viewModel.username.collectAsState()

        SettingsScreen(
            baseUrl = baseUrl,
            username = username,
            onEditBaseUrl = { showBaseUrlSheet = true },
            onEditUsername = { showUsernameSheet = true },
            onConnect = viewModel::connect,
            snackbarHostState = snackbarHostState
        )

        if (showBaseUrlSheet) {
            EditSettingBottomSheet(
                title = "Edit Base URL",
                currentValue = baseUrl,
                onSave = viewModel::saveBaseUrl,
                onDismiss = { showBaseUrlSheet = false }
            )
        }

        if (showUsernameSheet) {
            EditSettingBottomSheet(
                title = "Edit Username",
                currentValue = username,
                onSave = viewModel::saveUsername,
                onDismiss = { showUsernameSheet = false }
            )
        }
    } else {
        PinChallengeScreen(onPinVerified = { isVerified = true })
    }
}
