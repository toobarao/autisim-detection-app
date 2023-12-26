package com.example.compose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.R
import com.example.compose.components.ButtonComponent
import com.example.compose.components.ClickableLoginTextComponent
import com.example.compose.components.DividerTextComponent
import com.example.compose.components.HeadingTextComponent
import com.example.compose.components.MyTextFieldComponent
import com.example.compose.components.NormalTextComponent
import com.example.compose.components.PasswordTextFieldComponent
import com.example.compose.components.UnderlinedTextComponent
import com.example.compose.navigation.PostOfficeAppRouter
import com.example.compose.navigation.Screen
import com.example.compose.navigation.SystemBackButtonHandler

@Composable
fun LoginScreen(){
    Surface(
        modifier= Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ){
        Column ( modifier= Modifier
            .fillMaxSize()){
            NormalTextComponent(value = stringResource(id = R.string.login))
            HeadingTextComponent(value = stringResource(id = R.string.welcome) )
            Spacer(modifier=Modifier.height(20.dp))
            MyTextFieldComponent(labelValue = stringResource(id = R.string.email), painterResource = painterResource(
                id = R.drawable.message
            ) )
            PasswordTextFieldComponent(labelValue = stringResource(id = R.string.password), painterResource = painterResource(
                id = R.drawable.lock
            ) )
            Spacer(modifier=Modifier.height(40.dp))
            UnderlinedTextComponent(value = stringResource(id = R.string.forgot_password))
            Spacer(modifier=Modifier.height(80.dp))
            ButtonComponent(value = stringResource(id = R.string.login))
            Spacer(modifier=Modifier.height(20.dp))
            DividerTextComponent()
            Spacer(modifier=Modifier.height(20.dp))
            ClickableLoginTextComponent(false,onTextSelected = { PostOfficeAppRouter.navigateTo(
                Screen.SignUpScreen)})

        }
        SystemBackButtonHandler {
            PostOfficeAppRouter.navigateTo(Screen.SignUpScreen)
        }


    }
}
@Preview
@Composable
fun LoginScreenPreview(){
LoginScreen()
}
