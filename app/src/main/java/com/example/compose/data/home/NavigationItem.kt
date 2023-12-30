package com.example.compose.data.home

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val title:String,
    val description:String,
    val itemId:String,
    val icon: ImageVector
)
