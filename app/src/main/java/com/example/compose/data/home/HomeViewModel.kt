package com.example.compose.data.home



import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.compose.navigation.Screen
import com.example.compose.utility.DataRepository
import com.google.firebase.auth.FirebaseAuth

class HomeViewModel(application: Application): AndroidViewModel(application) {
    private val TAG=HomeViewModel::class.simpleName


    private val dataRepository: DataRepository = DataRepository(application)
    fun logout(navController: NavController){
        val firebaseAuth= FirebaseAuth.getInstance()
        firebaseAuth.signOut()
        val authStateListener= FirebaseAuth.AuthStateListener {
            if (it.currentUser == null) {
                Log.d(TAG, "Signout Success")
                dataRepository.saveTokenWithExpiry(null,0)
                navController.navigate(Screen.LoginScreen.route)
               // PostOfficeAppRouter.navigateTo(Screen.LoginScreen)
            } else {
                Log.d(TAG, "Signout not Success")
            }

        }
        firebaseAuth.addAuthStateListener(authStateListener)
    }




}