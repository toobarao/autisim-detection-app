package com.example.compose.data.signup


import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavController
import com.example.compose.data.profile.AddingUser
import com.example.compose.data.profile.readingUserData
import com.example.compose.data.rules.Validator
import com.example.compose.navigation.Screen
import com.example.compose.utility.DataRepository
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException

class SignupViewModel(application: Application): AndroidViewModel(application){

    var registrationUIState= mutableStateOf(SignupUIState())

    val signUpInProgress= mutableStateOf(false)
    private val dataRepository: DataRepository = DataRepository(application)

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


        val hasError = listOf(
            fNameResult,
            lNameResult,
            emailResult,
            passwordResult,

        ).any { !it.successful }

        return if(hasError) {
            registrationUIState.value = registrationUIState.value.copy(
                firstNameError =fNameResult.errorMessage ,
                lastNameError =lNameResult.errorMessage,
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,

            )
            false

        } else {
            true
        }

    }


    private fun createUserInFireBase(navController: NavController,Username:String,email:String,password:String){
        signUpInProgress.value = true
        val auth = FirebaseAuth.getInstance()

            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                Log.d(TAG,"isSuccessful=${it.isSuccessful}")
                if(it.isSuccessful){

                    val user = FirebaseAuth.getInstance().currentUser
                    if (user != null) {
                        // Access the user's authentication token
                        user.getIdToken(true)
                            .addOnCompleteListener { tokenTask ->
                                if (tokenTask.isSuccessful) {
                                    val token = tokenTask.result?.token
                                    val expirationTimeMillis = (tokenTask.result?.expirationTimestamp ?: 0)*1000
                                    Log.d("mytag","expirytime"+expirationTimeMillis.toString())
                                    Log.d("mytag","systemtime"+System.currentTimeMillis() .toString())

                                    token?.let{
                                        dataRepository.saveTokenWithExpiry(token,expirationTimeMillis)
                                        Log.d(TAG,token.toString())}


                                } else {
                                    Log.d(TAG,"Token fail")

                                }
                            }
                    }
                } else {

                }
                    signUpInProgress.value = false
                    AddingUser(Username,email)
                readingUserData(dataRepository)
                    navController.navigate(Screen.HomeScreen.route)


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