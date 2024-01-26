package com.example.compose.navigation

sealed class Screen(val route:String) {

    object SignUpScreen : Screen("signup_screen")
    object TermsAndConditionsScreen : Screen("terms_condition_screen")
    object LoginScreen : Screen("login_screen")
    object HomeScreen : Screen("home_screen")
    object  MainScreen:Screen("main_screen")

    object AboutScreen:Screen("about_screen")

    object  ProfileScreen:Screen("profile_screen")
    object NoInternetConnectionScreen:Screen("no_internet_con")
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



