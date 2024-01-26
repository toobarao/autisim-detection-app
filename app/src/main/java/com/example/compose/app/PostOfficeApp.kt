package com.example.compose.app


import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.navigation.Screen
import com.example.compose.screens.AboutScreen
import com.example.compose.screens.ForgotPasswordScreen
import com.example.compose.screens.HomeScreen
import com.example.compose.screens.LoginScreen
import com.example.compose.screens.MainScreen
import com.example.compose.screens.NoInternetConnectionScreen
import com.example.compose.screens.ProfileScreen
import com.example.compose.screens.SignUpScreen
import com.example.compose.screens.TermsAndConditionsScreen
import com.example.compose.utility.checkLoginState
import com.example.compose.utility.isInternetConnected


@Composable
fun PostOfficeApp(){
    val context= LocalContext.current
    val isLoggedIn = checkLoginState(context)
    val internetCheck= isInternetConnected(context)
    Log.d("mytag",isLoggedIn.toString())
    val startDestination = if(!internetCheck) Screen.NoInternetConnectionScreen.route else if(isLoggedIn) Screen.HomeScreen.route else Screen.MainScreen.route
    Surface(modifier = Modifier.fillMaxSize()
        //, color= Color.White
    ) {
        val navController=rememberNavController()
        NavHost(navController,startDestination=startDestination){
            composable(Screen.MainScreen.route){
                MainScreen(navController)
            }
            composable(Screen.NoInternetConnectionScreen.route){
                NoInternetConnectionScreen()
            }
            composable(Screen.HomeScreen.route){
                HomeScreen(navController)
            }
            composable(Screen.LoginScreen.route){
                LoginScreen(navController)
            }
            composable(Screen.ProfileScreen.route){
                ProfileScreen(navController)
            }
            composable(Screen.SignUpScreen.route){
                SignUpScreen(navController)
            }
            composable(Screen.AboutScreen.route){

                AboutScreen(navController)
            }
            composable(Screen.TermsAndConditionsScreen.route){
                TermsAndConditionsScreen(navController)
            }
            composable(Screen.ForgotPasswordScreen.route){
                ForgotPasswordScreen(navController)
            }
        }
    }
}


