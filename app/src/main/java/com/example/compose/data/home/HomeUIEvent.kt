package com.example.compose.data.home

import com.example.compose.data.login.loginUIEvent
import com.example.compose.data.signup.SignupUIEvent


sealed class HomeUIEvent{
    object HomeButtonClicked: HomeUIEvent()
    object LoginButtonClicked:HomeUIEvent()

    object ProfileButtonClicked:HomeUIEvent()



}