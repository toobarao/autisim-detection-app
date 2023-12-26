package com.example.compose.app

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.material3.Surface


import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.compose.navigation.PostOfficeAppRouter
import com.example.compose.navigation.Screen
import com.example.compose.screens.LoginScreen
import com.example.compose.screens.SignUpScreen

import com.example.compose.screens.TermsAndConditionsScreen


@Composable
fun PostOfficeApp(){
    Surface(modifier = Modifier.fillMaxSize(),
    color= Color.White) {
        Crossfade(label ="crossFade",targetState = PostOfficeAppRouter.currentScreen) { currentState->
            when(currentState.value){
            is Screen.SignUpScreen->{
                SignUpScreen()
            }
            is Screen.TermsAndConditionsScreen->{
                TermsAndConditionsScreen()
            }
                is Screen.LoginScreen->{
                    LoginScreen()
                }

                else -> {}
            }

        }

    }
}



