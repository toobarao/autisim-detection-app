package com.example.compose.data.home

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.compose.navigation.Screen

data class NavigationItem(
    val title:String,
    val description:String,
    val itemId: Screen,
    val icon: ImageVector
)
