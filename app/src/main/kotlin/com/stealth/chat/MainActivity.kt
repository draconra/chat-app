package com.stealth.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.stealth.chat.ui.bottomnav.BottomNavItem
import com.stealth.chat.ui.theme.ChatAppTheme

class MainActivity : AppCompatActivity() {

    private val tabs = listOf(
        BottomNavItem.Chats,
        BottomNavItem.Contacts,
        BottomNavItem.Profile
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(tabs.first().fragment)
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val bottomNav = findViewById<ComposeView>(R.id.bottom_nav_bar)
        bottomNav.setContent {
            ChatAppTheme {
                var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

                NavigationBar(modifier = Modifier.height(58.dp)) {
                    tabs.forEachIndexed { index, item ->
                        NavigationBarItem(
                            modifier = Modifier.height(20.dp),
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) },
                            selected = selectedTabIndex == index,
                            onClick = {
                                if (selectedTabIndex != index) {
                                    selectedTabIndex = index
                                    loadFragment(item.fragment)
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .commit()
    }
}
