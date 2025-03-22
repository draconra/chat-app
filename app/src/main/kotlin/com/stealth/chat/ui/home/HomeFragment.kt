package com.stealth.chat.ui.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.gson.Gson
import com.stealth.chat.ui.chat.ChatActivity
import com.stealth.chat.ui.chat.list.ChatListViewModel

class HomeFragment : Fragment() {

    private val chatListViewModel: ChatListViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = ComposeView(requireContext())

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (view as ComposeView).setContent {
            HomeScreen(viewModel = chatListViewModel, onChatClick = { selectedChat ->
                val chatJson = Gson().toJson(selectedChat)
                val intent = Intent(requireContext(), ChatActivity::class.java).apply {
                    putExtra("chatData", chatJson)
                }
                startActivity(intent)
            }, onNewChatClick = { chat ->
                val intent = Intent(context, ChatActivity::class.java)
                intent.putExtra("chatData", Gson().toJson(chat))
                startActivity(intent)
            })
        }
    }
}
