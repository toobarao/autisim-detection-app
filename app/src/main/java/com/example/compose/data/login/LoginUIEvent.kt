package com.example.compose.data.login

import androidx.navigation.NavController

sealed class loginUIEvent {

    data class EmailChanged(val email:String): loginUIEvent()
    data class PasswordChanged(val password:String): loginUIEvent()
    data class LoginButtonClicked(val navController: NavController): loginUIEvent()
}
