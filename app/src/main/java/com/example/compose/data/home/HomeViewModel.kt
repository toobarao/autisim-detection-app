package com.example.compose.data.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.compose.data.profile.Users
//import com.example.compose.navigation.PostOfficeAppRouter
import com.example.compose.navigation.Screen

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class HomeViewModel: ViewModel() {
    private val TAG=HomeViewModel::class.simpleName


    val isUserLoggedIn: MutableLiveData<Boolean> = MutableLiveData()

    fun logout(navController: NavController){
        val firebaseAuth= FirebaseAuth.getInstance()
        firebaseAuth.signOut()
        val authStateListener= FirebaseAuth.AuthStateListener {
            if (it.currentUser == null) {
                Log.d(TAG, "Signout Success")
                navController.navigate(Screen.LoginScreen.route)
               // PostOfficeAppRouter.navigateTo(Screen.LoginScreen)
            } else {
                Log.d(TAG, "Signout not Success")
            }

        }
        firebaseAuth.addAuthStateListener(authStateListener)
    }
    fun checkForActiveSession() {
        if (FirebaseAuth.getInstance().currentUser != null) {
            Log.d(TAG, "Valid session")
            isUserLoggedIn.value = true
        } else {
            Log.d(TAG, "User is not logged in")
            isUserLoggedIn.value = false
        }
    }


    val emailId: MutableLiveData<String> = MutableLiveData()

    fun getUserData() {
        FirebaseAuth.getInstance().currentUser?.also {
            it.email?.also { email ->
                emailId.value = email
            }
        }
    }




}