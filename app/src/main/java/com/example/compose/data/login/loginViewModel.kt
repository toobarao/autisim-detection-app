package com.example.compose.data.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.compose.data.rules.Validator
import com.example.compose.navigation.PostOfficeAppRouter
import com.example.compose.navigation.Screen
import com.google.firebase.auth.FirebaseAuth


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
                 printState()
            }
        }}



    private fun validateDataWithRules():Boolean {
        val emailResult= Validator.validateEmail(email = loginUIState.value.email)
        val passwordResult= Validator.validatePassword(password = loginUIState.value.password)
        Log.d(TAG,"validator with rules")
        Log.d(TAG,"email=$emailResult")
        Log.d(TAG,"password=$passwordResult")
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

    private fun printState(){
        Log.d(TAG,"Initial_printState")
        Log.d(TAG,loginUIState.value.toString())
        login(loginUIState.value.email,loginUIState.value.password)
    }
    private fun login(email:String,password:String){
        loginInProgress.value=true
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { Log.d(TAG,"isSuccessful=${it.isSuccessful}")
                if(it.isSuccessful){
                    loginInProgress.value=false
                    PostOfficeAppRouter.navigateTo(Screen.HomeScreen)
                } }
            .addOnFailureListener {
                loginInProgress.value=false
                Log.d(TAG,"Not Successful=${it.message}")
                Log.d(TAG,"Not Successful=${it.localizedMessage}") }
    }
}