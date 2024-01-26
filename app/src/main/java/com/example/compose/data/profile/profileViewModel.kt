package com.example.compose.data.profile

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.example.compose.data.rules.Validator
import com.example.compose.utility.DataRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

class profileViewModel(application: Application): AndroidViewModel(application) {


    private val TAG = profileViewModel::class.simpleName

private val dataRepository: DataRepository = DataRepository(application)
    var profileUIState= mutableStateOf(profileUIState())
    fun onEvent(event: profileUIEvent){
        when (event){

            is profileUIEvent.EmailChanged->{
                profileUIState.value=profileUIState.value.copy(email = event.email)
            }
            is profileUIEvent.NameChanged->{
                profileUIState.value=profileUIState.value.copy(name = event.Name)
            }
            is profileUIEvent.SaveButtonClicked->{
                if(validateDataWithRules())
                    updateUserProfile(profileUIState.value.name,profileUIState.value.email)
            }
        }}
    private fun validateDataWithRules():Boolean {
        val emailResult= Validator.validateEmail(email = profileUIState.value.email)
        val nameResult= Validator.validateFirstName(fName=profileUIState.value.name)
        val hasError = listOf(
            emailResult,
            nameResult,
        ).any { !it.successful }

        return if(hasError) {
            profileUIState.value = profileUIState.value.copy(
                emailError =emailResult.errorMessage,
                nameError = nameResult.errorMessage,

                )
            false

        } else {
            true
        }

    }

fun readProfileData(key:String):String{
    return dataRepository.readDataFromSharedPreferences(key)

}
    fun writeUserData(name:String?,email:String?){
        dataRepository.writeToSharedPreferencesText(name, email)
    }

    fun updateUserProfile(name: String, email: String) {
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null && name.isNotEmpty() && email.isNotEmpty()) {
            val userIdReference = Firebase.database.reference
                .child("users").child(currentUser.uid)

            val userDataUpdates = HashMap<String, Any>()
            userDataUpdates["name"] = name
            userDataUpdates["email"] = email

           dataRepository.writeToSharedPreferencesText(name,email)
            userIdReference.updateChildren(userDataUpdates)
                .addOnSuccessListener {
                }
                .addOnFailureListener { e ->
                    profileUIState.value = profileUIState.value.copy(
                        profileErrorMessage = e.localizedMessage,
                        )
                }
        } else {
            profileUIState.value = profileUIState.value.copy(
                profileErrorMessage = "Updation Unsuccessfull",
            )
            // Handle cases where user is not logged in or name/email is empty
        }
    }



    fun updateImageUriToDatabase(newImageUri: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        userId?.let { uid ->
            val database = FirebaseDatabase.getInstance().reference.child("users").child(uid)
            database.child("imageUri").setValue(newImageUri)
            dataRepository.writeToSharedPreferencesImage(newImageUri)

        }
    }


}