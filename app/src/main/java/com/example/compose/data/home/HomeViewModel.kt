package com.example.compose.data.home

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
            itemId="HomeScreen"
        ),
        NavigationItem(
            title = "Setting",
            icon= Icons.Default.Settings,
            description = "Settings",
            itemId="SettingScreen"
        ),
        NavigationItem(
            title = "Favourites",
            icon= Icons.Default.Favorite,
            description = "Favourite",
            itemId="FavouriteScreen"
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