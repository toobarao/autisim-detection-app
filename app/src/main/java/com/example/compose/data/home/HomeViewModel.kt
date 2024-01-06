package com.example.compose.data.home

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.compose.R
import com.example.compose.data.login.loginUIEvent
import com.example.compose.navigation.PostOfficeAppRouter
import com.example.compose.navigation.Screen
import com.google.firebase.auth.FirebaseAuth

class HomeViewModel: ViewModel() {
    private val TAG=HomeViewModel::class.simpleName

    val navigationItemsList =listOf<NavigationItem>(
        NavigationItem(
            title = "Home",
            icon= Icons.Default.Home,
            description = "Home Screen",
            itemId=Screen.HomeScreen
        ),
        NavigationItem(
            title = "Profile",
            icon= Icons.Default.AccountBox,
            description = "Profile Screen",
            itemId=Screen.ProfileScreen
        ),
        NavigationItem(
            title = "About",
            icon= Icons.Default.Help,
            description = "About Screen",
            itemId=Screen.AboutScreen
        )



    )
    val isUserLoggedIn: MutableLiveData<Boolean> = MutableLiveData()

    fun logout(){
        val firebaseAuth= FirebaseAuth.getInstance()
        firebaseAuth.signOut()
        val authStateListener= FirebaseAuth.AuthStateListener {
            if (it.currentUser == null) {
                Log.d(TAG, "Signout Success")
                PostOfficeAppRouter.navigateTo(Screen.LoginScreen)
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