package com.example.compose.data.login

import com.example.compose.data.signup.SignupUIEvent

sealed class loginUIEvent {

    data class EmailChanged(val email:String): loginUIEvent()
    data class PasswordChanged(val password:String): loginUIEvent()
    object LoginButtonClicked: loginUIEvent()
}
