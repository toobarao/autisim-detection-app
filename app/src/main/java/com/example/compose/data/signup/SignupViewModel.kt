package com.example.compose.data.signup

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.compose.data.profile.Users
import com.example.compose.data.profile.profileViewModel
import com.example.compose.data.rules.Validator
//import com.example.compose.navigation.PostOfficeAppRouter
import com.example.compose.navigation.Screen
import com.google.firebase.Firebase
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.database.database

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
            {
                createUserInFireBase(event.navController,registrationUIState.value.firstName+registrationUIState.value.lastName,email=registrationUIState.value.email, password =registrationUIState.value.password )
            }
        }
    }

    }



    private fun validateDataWithRules():Boolean{
       val fNameResult=Validator.validateFirstName(fName = registrationUIState.value.firstName)
        val lNameResult=Validator.validateLastName(lName = registrationUIState.value.lastName)
        val emailResult=Validator.validateEmail(email = registrationUIState.value.email)
        val passwordResult=Validator.validatePassword(password = registrationUIState.value.password)
        val privacyPolicyResult = Validator.validatePrivacyPolicyAcceptance(
            statusValue = registrationUIState.value.privacyPolicyAccepted
        )


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
    fun AddingUser(name: String, email: String) {
        Log.d(TAG, "Initial Profile data")
        Log.d(TAG, "Name" + name + "Email" + email)
        val user = FirebaseAuth.getInstance().currentUser
        user?.run {
            val userIdReference = Firebase.database.reference
                .child("users").child(user.uid)
            val userData = Users(
                name = name,
                email = email,
                imageUri = null
            )
            userIdReference.setValue(userData)
        }
    }

    private fun createUserInFireBase(navController: NavController,Username:String,email:String,password:String){
        signUpInProgress.value = true
        val auth = FirebaseAuth.getInstance()

            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                Log.d(TAG,"isSuccessful=${it.isSuccessful}")
                if(it.isSuccessful){
                    signUpInProgress.value = false
                    AddingUser(Username,email)
                    navController.navigate(Screen.HomeScreen.route)
                    //PostOfficeAppRouter.navigateTo(Screen.HomeScreen)
                }
            }.addOnFailureListener {exception->
                    signUpInProgress.value = false
                    val errorMessage = when (exception) {
                        is FirebaseAuthInvalidUserException -> "Invalid user"
                        is FirebaseAuthInvalidCredentialsException -> "Invalid credentials"
                        is FirebaseAuthUserCollisionException->"Email already exists"
                        is FirebaseNetworkException->"Network Error"
                        else -> exception.localizedMessage ?: "Unknown error occurred"
                    }
                registrationUIState.value.signupErrorMessage = errorMessage

            }




    }


}