package com.example.compose.app

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.material3.Surface


import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
//import com.example.compose.navigation.PostOfficeAppRouter
import com.example.compose.navigation.Screen
import com.example.compose.screens.AboutScreen
import com.example.compose.screens.ForgotPasswordScreen
import com.example.compose.screens.HomeScreen
import com.example.compose.screens.LoginScreen
import com.example.compose.screens.MainScreen
import com.example.compose.screens.ProfileScreen
import com.example.compose.screens.SignUpScreen

import com.example.compose.screens.TermsAndConditionsScreen


@Composable
fun PostOfficeApp(){
    Surface(modifier = Modifier.fillMaxSize(),
        color= Color.White) {
        val navController=rememberNavController()
        NavHost(navController,Screen.MainScreen.route){
            composable(Screen.MainScreen.route){
                MainScreen(navController)
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
//            composable(Screen.DetailScreen.route+"/{name}",
//                listOf(navArgument("name"){
//                    type= NavType.StringType
//                    defaultValue="too"
//                    nullable=true
//                })){entry->
//                DetailScreen(name = entry.arguments?.getString("name"),navController)
//
//            }
        }

    }
}



