package com.example.compose.utility

import android.app.Application
import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth

fun checkLoginState(context: Context): Boolean {

    val dataRepository = DataRepository(context.applicationContext as Application)
    val authToken = dataRepository.getToken()
    val expiryTime = dataRepository.getExpiryTime()
    Log.d("mytag","token"+authToken.toString())
    Log.d("mytag","expirytime"+expiryTime.toString())
    Log.d("mytag","systemtime"+System.currentTimeMillis() .toString())
    if (authToken != null && System.currentTimeMillis() < expiryTime) {

        return true
    } else {

        val refreshedToken = refreshAuthToken(context)
        return refreshedToken != null
    }
}
private fun refreshAuthToken(context: Context): String? {
    val dataRepository = DataRepository(context.applicationContext as Application)
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser

    return if (user != null) {

        user.getIdToken(true)
            .addOnCompleteListener { tokenTask ->
                if (tokenTask.isSuccessful) {
                    val refreshedToken = tokenTask.result?.token
                    val expirationTimeMillis = (tokenTask.result?.expirationTimestamp ?: 0) * 1000


                    refreshedToken?.let {
                        dataRepository.saveTokenWithExpiry(it, expirationTimeMillis)
                    }
                } else {

                }
            }

        null
    } else {

        null
    }
}