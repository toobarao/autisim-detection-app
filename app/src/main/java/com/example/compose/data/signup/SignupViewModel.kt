package com.example.compose.data.signup

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.compose.app.PostOfficeApp
import com.example.compose.data.rules.Validator
import com.example.compose.navigation.PostOfficeAppRouter
import com.example.compose.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener

class SignupViewModel:ViewModel() {

    var registrationUIState= mutableStateOf(SignupUIState())
    var allValidationPassed= mutableStateOf(false)
    val signUpInProgress= mutableStateOf(false)

    private val TAG= SignupViewModel::class.simpleName
    fun onEvent(event: SignupUIEvent){
    when (event){
        is SignupUIEvent.FirstNameChanged->{
            registrationUIState.value=registrationUIState.value.copy(firstName = event.firstName)

        }
        is SignupUIEvent.LastNameChanged->{
            registrationUIState.value=registrationUIState.value.copy(lastName = event.lastName)

        }
        is SignupUIEvent.EmailChanged->{
            registrationUIState.value=registrationUIState.value.copy(email = event.email)

        }
        is SignupUIEvent.PasswordChanged->{
            registrationUIState.value=registrationUIState.value.copy(password = event.password)

        }
        is SignupUIEvent.PrivacyPolicyCheckBoxClicked -> {
            registrationUIState.value = registrationUIState.value.copy(
                privacyPolicyAccepted = event.status
            )
            Log.d(TAG,registrationUIState.value.privacyPolicyAccepted.toString())
        }
        is SignupUIEvent.RegisterButtonClicked->{

            if(validateDataWithRules())
            {signup()}
        }
    }

    }

    private fun signup() {
        Log.d(TAG,"Initial_signup")
        printState()
        createUserInFireBase(email=registrationUIState.value.email, password =registrationUIState.value.password )
//        validateDataWithRules()
    }

    private fun validateDataWithRules():Boolean{
       val fNameResult=Validator.validateFirstName(fName = registrationUIState.value.firstName)
        val lNameResult=Validator.validateLastName(lName = registrationUIState.value.lastName)
        val emailResult=Validator.validateEmail(email = registrationUIState.value.email)
        val passwordResult=Validator.validatePassword(password = registrationUIState.value.password)
        val privacyPolicyResult = Validator.validatePrivacyPolicyAcceptance(
            statusValue = registrationUIState.value.privacyPolicyAccepted
        )
        Log.d(TAG,"validator with rules")
        Log.d(TAG,"firstName=$fNameResult")
        Log.d(TAG,"lastName=$lNameResult")
        Log.d(TAG,"email=$emailResult")
        Log.d(TAG,"password=$passwordResult")

        val hasError = listOf(
            fNameResult,
            lNameResult,
            emailResult,
            passwordResult,
            privacyPolicyResult
        ).any { !it.successful }

        return if(hasError) {
            registrationUIState.value = registrationUIState.value.copy(
                firstNameError =fNameResult.errorMessage ,
                lastNameError =lNameResult.errorMessage,
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                privacyPolicyError =privacyPolicyResult.errorMessage
            )
            false

        } else {
            true
        }

    }

    private fun printState(){
        Log.d(TAG,"Initial_printState")
        Log.d(TAG,registrationUIState.value.toString())
    }
    private fun createUserInFireBase(email:String,password:String){
        signUpInProgress.value = true
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { Log.d(TAG,"isSuccessful=${it.isSuccessful}")
                if(it.isSuccessful){
                    Log.d(TAG,"navigated to home scree")
                    PostOfficeAppRouter.navigateTo(Screen.HomeScreen)
                }
            }

            .addOnFailureListener {
                signUpInProgress.value = false
                Log.d(TAG,"Not Successful=${it.message}")
                Log.d(TAG,"Not Successful=${it.localizedMessage}")}
    }


}