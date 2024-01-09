package com.example.compose.data.home

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.compose.navigation.Screen

data class NavigationItem(
    val title:String,
    val description:String,
   val route:String,
    val icon: ImageVector
)
data class DrawerItems(

    val icon : ImageVector,
    val text : String,
    val badgeCount : Int,
    val hasBadge : Boolean
)
