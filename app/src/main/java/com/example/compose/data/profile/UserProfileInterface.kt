package com.example.compose.data.profile

import java.lang.Exception

interface UserProfileListener {
    fun onProfileDataReceived(userData: Users)
    fun onProfileError()
}