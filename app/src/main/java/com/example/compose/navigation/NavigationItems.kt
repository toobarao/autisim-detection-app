package com.example.compose.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import com.example.compose.data.home.DrawerItems
import com.example.compose.data.home.NavigationItem


val navigationItemsList =listOf<NavigationItem>(
        NavigationItem(
            title = "Home",
            icon= Icons.Default.Home,
            description = "Home Screen",
            route=Screen.HomeScreen.route
        ),
        NavigationItem(
            title = "Profile",
            icon= Icons.Default.AccountBox,
            description = "Profile Screen",
            route = Screen.ProfileScreen.route
        ),
        NavigationItem(
            title = "About",
            icon= Icons.Default.Help,
            description = "About Screen",
            route=Screen.AboutScreen.route
        )



    )
val extraItems= listOf(
    DrawerItems(Icons.Default.Share, "Share", 0, false),
    DrawerItems(Icons.Filled.Star, "Rate", 0, false)
)