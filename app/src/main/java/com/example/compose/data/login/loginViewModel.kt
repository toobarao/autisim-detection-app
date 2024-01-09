package com.example.compose.data.login

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.compose.data.rules.Validator
//import com.example.compose.navigation.PostOfficeAppRouter
import com.example.compose.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import android.content.Context
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.compose.data.home.HomeViewModel
import com.example.compose.data.profile.UserProfileListener
import com.example.compose.data.profile.Users
import com.example.compose.data.profile.profileViewModel
import com.google.firebase.Firebase
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class loginViewModel:ViewModel() {

    var loginUIState= mutableStateOf(loginUIState())
    val loginInProgress= mutableStateOf(false)

    private val TAG= loginViewModel::class.simpleName
    fun onEvent(event: loginUIEvent){
        when (event){

            is loginUIEvent.EmailChanged->{
                loginUIState.value=loginUIState.value.copy(email = event.email)
            }
            is loginUIEvent.PasswordChanged->{
                loginUIState.value=loginUIState.value.copy(password = event.password)
            }
            is loginUIEvent.LoginButtonClicked->{
                if(validateDataWithRules())
                login(event.navController,loginUIState.value.email, loginUIState.value.password)
            }
        }}



    private fun validateDataWithRules():Boolean {
        val emailResult= Validator.validateEmail(email = loginUIState.value.email)
        val passwordResult= Validator.validatePassword(password = loginUIState.value.password)
        val hasError = listOf(
            emailResult,
            passwordResult,
        ).any { !it.successful }

        return if(hasError) {
            loginUIState.value = loginUIState.value.copy(
                emailError =emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,

            )
            false

        } else {
            true
        }

    }



    private fun login(navController: NavController, email:String, password:String){
        loginInProgress.value=true
        val auth = FirebaseAuth.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful){
                loginInProgress.value=false
                Log.d(TAG,"Login done")
                navController.navigate(Screen.HomeScreen.route)
                //PostOfficeAppRouter.navigateTo(Screen.HomeScreen)
            }
        }.addOnFailureListener {exception ->
            loginInProgress.value=false
            val errorMessage = when (exception) {
                is FirebaseAuthInvalidUserException -> "Invalid user"
                is FirebaseAuthInvalidCredentialsException -> "Invalid credentials"
                is FirebaseNetworkException->"Network Error"
                else -> exception.localizedMessage ?: "Unknown error occurred"
            }
            loginUIState.value.loginErrorMessage = errorMessage


            }


            }
    }
