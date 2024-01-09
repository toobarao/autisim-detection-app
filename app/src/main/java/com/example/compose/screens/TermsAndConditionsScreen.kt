package com.example.compose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.compose.R
import com.example.compose.components.HeadingTextComponent
//import com.example.compose.navigation.PostOfficeAppRouter


@Composable
fun TermsAndConditionsScreen(navController: NavController){
    Surface(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
        .padding(16.dp)){
        HeadingTextComponent(value = stringResource(id = R.string.terms_and_condition))

    }
//    SystemBackButtonHandler {
//        PostOfficeAppRouter.navigateTo(Screen.SignUpScreen)
//    }
}

//@Preview
//@Composable
//fun TermsAndConditionsScreenPreview(){
//    TermsAndConditionsScreen()
//}