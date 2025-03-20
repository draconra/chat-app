package com.stealth.chat.ui.bottomnav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.fragment.app.Fragment
import com.stealth.chat.ui.home.HomeFragment
import com.stealth.chat.ui.notifications.NotificationsFragment

sealed class BottomNavItem(val label: String, val icon: ImageVector, val fragment: Fragment) {
    data object Chats : BottomNavItem("Chats", Icons.Default.Email, HomeFragment())
    data object Contacts : BottomNavItem("History", Icons.Default.AccountBox, NotificationsFragment())
    data object Profile : BottomNavItem("Profile", Icons.Default.Person, NotificationsFragment())
}
