package com.example.compose.utility

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit

class DataRepository(application: Application) {
    private val sharedPreferences: SharedPreferences =
        application.getSharedPreferences("myToken", Context.MODE_PRIVATE)
    val profilePreferences=application.getSharedPreferences("myPref", Context.MODE_PRIVATE)

    fun saveTokenWithExpiry(token: String?, expiryTime: Long) {
        sharedPreferences.edit {
            putString("authToken", token)
            putLong("expiryTime", expiryTime)
        }
    }

    fun getToken(): String? {
        return sharedPreferences.getString("authToken", null)
    }

    fun getExpiryTime(): Long {
        return sharedPreferences.getLong("expiryTime", 0)
    }
    fun writeToSharedPreferences(name: String?,email:String?,imageUrl: String?) {
       // Log.d(TAG, email+name)
        profilePreferences.edit {
            putString("name", name)
            putString("email",email)
            putString("imageUri",imageUrl)
            apply()
        }
    }
    fun writeToSharedPreferencesText(name: String?,email:String?) {
       // Log.d(TAG, email+name)
        profilePreferences.edit {
            putString("name", name)
            putString("email",email)
            apply()
        }
    }
    fun writeToSharedPreferencesImage(imageUrl: String?) {
        // Log.d(TAG, email+name)
        profilePreferences.edit {
            putString("imageUri",imageUrl)
            apply()
        }
    }


    fun readDataFromSharedPreferences(key:String): String {
       // Log.d(TAG,"we are in read"+key)
       // Log.d(TAG,sharedPref.getString(key, "") ?: "")
        return profilePreferences.getString(key, "") ?: ""
    }

}