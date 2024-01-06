package com.example.compose.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class Screen {

    object SignUpScreen : Screen()
    object TermsAndConditionsScreen : Screen()
    object LoginScreen : Screen()
    object HomeScreen : Screen()
    object  MainScreen:Screen()

    object AboutScreen:Screen()

    object  ProfileScreen:Screen()
}


object PostOfficeAppRouter {

    var currentScreen: MutableState<Screen> = mutableStateOf(Screen.MainScreen)

    fun navigateTo(destination: Screen) {
        currentScreen.value = destination
    }
}
