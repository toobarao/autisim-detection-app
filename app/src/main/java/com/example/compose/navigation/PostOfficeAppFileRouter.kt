package com.example.compose.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class Screen(val route:String) {

    object SignUpScreen : Screen("signup_screen")
    object TermsAndConditionsScreen : Screen("terms_condition_screen")
    object LoginScreen : Screen("login_screen")
    object HomeScreen : Screen("home_screen")
    object  MainScreen:Screen("main_screen")

    object AboutScreen:Screen("about_screen")

    object  ProfileScreen:Screen("profile_screen")
    object ForgotPasswordScreen:Screen("forgot_password_screen")

    fun withArgs(vararg args:String):String{
        return buildString {
            append(route)
            args.forEach{
                    arg->
                append("/$arg")
            }
        }
    }
}



