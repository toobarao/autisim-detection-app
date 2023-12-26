package com.example.compose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn

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
import com.example.compose.components.CheckBoxComponent
import com.example.compose.components.ClickableLoginTextComponent
import com.example.compose.components.DividerTextComponent
import com.example.compose.components.HeadingTextComponent
import com.example.compose.components.MyTextFieldComponent
import com.example.compose.components.NormalTextComponent
import com.example.compose.components.PasswordTextFieldComponent
import com.example.compose.navigation.PostOfficeAppRouter
import com.example.compose.navigation.Screen

@Composable
fun SignUpScreen(){
Surface(
    modifier= Modifier
        .fillMaxSize()
        .background(Color.White)
        .padding(28.dp)
){
    Column(modifier=Modifier.fillMaxSize()){
      
NormalTextComponent(value = stringResource(id = R.string.hello))
HeadingTextComponent(value = stringResource(id = R.string.create_account))
        Spacer(modifier = Modifier.heightIn(20.dp))
        MyTextFieldComponent(labelValue = stringResource(id = R.string.firstName), painterResource(id = R.drawable.profile))
        MyTextFieldComponent(labelValue = stringResource(id = R.string.lastName), painterResource(id = R.drawable.profile))
        MyTextFieldComponent(labelValue = stringResource(id = R.string.email), painterResource(id = R.drawable.message))
        PasswordTextFieldComponent(labelValue = stringResource(id = R.string.password), painterResource(id = R.drawable.lock))
        CheckBoxComponent(stringResource(id = R.string.terms_and_condition),{
            PostOfficeAppRouter.navigateTo(Screen.TermsAndConditionsScreen)
        })
        Spacer(modifier = Modifier.heightIn(40.dp))
        ButtonComponent(stringResource(id =  R.string.register))
        Spacer(modifier = Modifier.heightIn(20.dp))
        DividerTextComponent()
        ClickableLoginTextComponent(tryingToLogin = true, onTextSelected = {
            PostOfficeAppRouter.navigateTo(Screen.LoginScreen)
        })

    }
}
}

@Preview
@Composable
fun DefaultPreviewSignUpScreen(){
    SignUpScreen()
}