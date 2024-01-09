package com.example.compose.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.compose.R
import com.example.compose.components.ButtonComponent
import com.example.compose.components.CheckBoxComponent
import com.example.compose.components.ClickableLoginTextComponent
import com.example.compose.components.DividerTextComponent
import com.example.compose.components.HeadingTextComponent
import com.example.compose.components.MyTextFieldComponent
import com.example.compose.components.NormalTextComponent
import com.example.compose.components.PasswordTextFieldComponent
import com.example.compose.components.errorMessage
import com.example.compose.data.signup.SignupUIEvent
import com.example.compose.data.signup.SignupViewModel

//import com.example.compose.navigation.PostOfficeAppRouter
import com.example.compose.navigation.Screen
import com.example.compose.ui.theme.colorError

@Composable
fun SignUpScreen(navController: NavController, signupViewModel: SignupViewModel = viewModel()){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

Surface(modifier= Modifier
    .fillMaxSize()
    .background(Color.White)
    .padding(28.dp))
{
    Column(modifier=Modifier.fillMaxSize()){

        NormalTextComponent(value = stringResource(id = R.string.hello))
        HeadingTextComponent(value = stringResource(id = R.string.create_account))

        Spacer(modifier = Modifier.heightIn(20.dp))

        MyTextFieldComponent(labelValue = stringResource(id = R.string.firstName), painterResource(id = R.drawable.profile), onTextSelected = { signupViewModel.onEvent(
            SignupUIEvent.FirstNameChanged(it)) })
        if (signupViewModel.registrationUIState.value.firstNameError != null) {
            errorMessage(errorMessage = signupViewModel.registrationUIState.value.firstNameError.toString())
        }

        MyTextFieldComponent(labelValue = stringResource(id = R.string.lastName), painterResource(id = R.drawable.profile), onTextSelected = {signupViewModel.onEvent(
            SignupUIEvent.LastNameChanged(it))})
        if (signupViewModel.registrationUIState.value.lastNameError != null) {
            errorMessage(errorMessage = signupViewModel.registrationUIState.value.lastNameError.toString())
        }
        MyTextFieldComponent(labelValue = stringResource(id = R.string.email), painterResource(id = R.drawable.message), onTextSelected = {signupViewModel.onEvent(
            SignupUIEvent.EmailChanged(it))})
        if (signupViewModel.registrationUIState.value.emailError != null) {
            errorMessage(errorMessage = signupViewModel.registrationUIState.value.emailError.toString())
        }
        PasswordTextFieldComponent(labelValue = stringResource(id = R.string.password), painterResource(id = R.drawable.lock), onTextSelected = {signupViewModel.onEvent(
            SignupUIEvent.PasswordChanged(it))})
        if (signupViewModel.registrationUIState.value.passwordError != null) {
            errorMessage(errorMessage = signupViewModel.registrationUIState.value.passwordError.toString())
        }

        CheckBoxComponent(stringResource(id = R.string.terms_and_condition),  onCheckedChange = {
            signupViewModel.onEvent(SignupUIEvent.PrivacyPolicyCheckBoxClicked(it))
        },
            onTextSelected = {
                navController.navigate(Screen.TermsAndConditionsScreen.route)
//                PostOfficeAppRouter.navigateTo(
//                    Screen.TermsAndConditionsScreen
//                )
            })


        if (signupViewModel.registrationUIState.value.privacyPolicyError != null) {
            errorMessage(errorMessage = signupViewModel.registrationUIState.value.privacyPolicyError.toString())
        }

        Spacer(modifier = Modifier.heightIn(40.dp))
        if (signupViewModel.registrationUIState.value.signupErrorMessage != null) {
            Text(
                text =signupViewModel.registrationUIState.value.signupErrorMessage.toString(),
                color = colorError,
        modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
        Spacer(modifier = Modifier.heightIn(40.dp))

        ButtonComponent(stringResource(id =  R.string.register), onButtonClicked = { signupViewModel.onEvent(SignupUIEvent.RegisterButtonClicked(navController)) })


        Spacer(modifier = Modifier.heightIn(20.dp))

        DividerTextComponent()

        ClickableLoginTextComponent(tryingToLogin = true, onTextSelected = {
            navController.navigate(Screen.LoginScreen.route)
//            PostOfficeAppRouter.navigateTo(Screen.LoginScreen)
        })




    }

}
        if(signupViewModel.signUpInProgress.value) {
            CircularProgressIndicator()
        }

    }
}

//@Preview
//@Composable
//fun DefaultPreviewSignUpScreen(){
//    SignUpScreen()
//}