package com.stealth.chat.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.stealth.chat.model.Chat
import com.stealth.chat.ui.chat.list.ChatListScreen
import com.stealth.chat.ui.chat.list.ChatListViewModel
import com.stealth.chat.ui.chat.newchat.NewChatBottomSheetDialog
import com.stealth.chat.ui.home.uicomponent.ChatTopBar
import com.stealth.chat.ui.theme.ChatAppTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    viewModel: ChatListViewModel,
    onChatClick: (Chat) -> Unit,
    onNewChatClick: (Chat) -> Unit = {}
) {
    val chats by viewModel.filteredChats.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val isSearchActive by viewModel.isSearchActive.collectAsState()
    val allContacts by viewModel.chats.collectAsState()

    var isNewChatSheetOpen by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.fetchMessages()
    }

    ChatAppTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                ChatTopBar(
                    isSearchActive = isSearchActive,
                    searchQuery = searchQuery,
                    onQueryChange = viewModel::updateSearchQuery,
                    onToggleSearch = {
                        if (isSearchActive) viewModel.clearSearch()
                        else viewModel.toggleSearchBar()
                    },
                    onNewChatClick = {
                        isNewChatSheetOpen = true
                    }
                )

                if (isNewChatSheetOpen) {
                    NewChatBottomSheetDialog(
                        contacts = allContacts,
                        onContactClick = {
                            isNewChatSheetOpen = false
                            onNewChatClick(it)
                        },
                        onDismiss = { isNewChatSheetOpen = false }
                    )
                }
            },
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                ChatListScreen(chats = chats, onChatClick = onChatClick)
            }
        }
    }
}