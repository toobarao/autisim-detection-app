package com.example.compose.data.login
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.compose.data.rules.Validator
import com.example.compose.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import androidx.navigation.NavController
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException


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

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {task->
            if(task.isSuccessful){
                val user = FirebaseAuth.getInstance().currentUser
                if (user != null) {
                    // Access the user's authentication token
                    user.getIdToken(true)
                        .addOnCompleteListener { tokenTask ->
                            if (tokenTask.isSuccessful) {
                                val token = tokenTask.result?.token
                                val expirationTimeMillis = tokenTask.result?.expirationTimestamp ?: 0
                                Log.d(TAG,token.toString())

                                // Now you have the authentication token (JWT)
                                // You can use this token for further authentication or API requests
                                // (Remember to handle token expiration and refresh as needed)
                            } else {
                                Log.d(TAG,"Token fail")
                                // Handle token retrieval failure
                            }
                        }
                }
            } else {
                // Handle authentication failure
            }
                loginInProgress.value=false
                Log.d(TAG,"Login done")
                navController.navigate(Screen.HomeScreen.route)

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
