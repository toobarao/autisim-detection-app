package com.example.compose.data.signup

import androidx.navigation.NavController

sealed class SignupUIEvent {
    data class FirstNameChanged(val firstName:String): SignupUIEvent()
    data class LastNameChanged(val lastName:String): SignupUIEvent()
    data class EmailChanged(val email:String): SignupUIEvent()
    data class PasswordChanged(val password:String): SignupUIEvent()



    data class RegisterButtonClicked(val navController: NavController): SignupUIEvent()
}