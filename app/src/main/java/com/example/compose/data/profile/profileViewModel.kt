package com.example.compose.data.profile

import android.app.Application
import android.content.Context
import android.util.Log

import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue

class profileViewModel(application: Application): AndroidViewModel(application) {
    val auth = FirebaseAuth.getInstance()

    private val TAG = profileViewModel::class.simpleName
    val sharedPref=application.getSharedPreferences("myPref", Context.MODE_PRIVATE)
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

    fun updateUserProfile(name: String, email: String, imageUrl: String) {
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null && name.isNotEmpty() && email.isNotEmpty()) {
            val userIdReference = Firebase.database.reference
                .child("users").child(currentUser.uid)

            val userDataUpdates = HashMap<String, Any>()
            userDataUpdates["name"] = name
            userDataUpdates["email"] = email
            //userDataUpdates["imageUri"] = imageUrl
            writeToSharedPreferencesText(name,email)
            userIdReference.updateChildren(userDataUpdates)
                .addOnSuccessListener {
                    // Update successful
                    // Any additional actions after successful update
                }
                .addOnFailureListener { e ->
                    // Update failed
                    // Handle failure, log, show error, etc.
                }
        } else {
            // Handle cases where user is not logged in or name/email is empty
        }
    }

    fun writeToSharedPreferences(name: String?,email:String?,imageUrl: String?) {
        Log.d(TAG, email+name)
        sharedPref.edit {
            putString("name", name)
            putString("email",email)
            putString("imageUri",imageUrl)
            apply()
        }
    }
    fun writeToSharedPreferencesText(name: String?,email:String?) {
        Log.d(TAG, email+name)
        sharedPref.edit {
            putString("name", name)
            putString("email",email)
            apply()
        }
    }


    fun readDataFromSharedPreferences(key:String): String {
        Log.d(TAG,"we are in read"+key)
        Log.d(TAG,sharedPref.getString(key, "") ?: "")
        return sharedPref.getString(key, "") ?: ""
    }

    fun readingUserData(){
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val databaseReference = FirebaseDatabase.getInstance().reference.child("users")
        userId?.let { uid ->
            val userReference = databaseReference.child(uid)

            userReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // User data exists, retrieve it
                        val userData = dataSnapshot.getValue<Users>()

                        writeToSharedPreferences(userData?.name,userData?.email,userData?.imageUri)
                        Log.d(TAG, "Value is: " + userData?.name)
                        // Do something with userData
                    } else {
                        // User data doesn't exist at this location
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.w(TAG, "Failed to read value.", databaseError.toException())
                    // Handle potential errors while retrieving data
                }
            })
        }

    }


    fun updateImageUriToDatabase(newImageUri: String) {
        // Use Firebase SDK to update the user's image URI in the Realtime Database
        // Assuming you have a reference to the Firebase Realtime Database
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        userId?.let { uid ->
            val database = FirebaseDatabase.getInstance().reference.child("users").child(uid)
            database.child("imageUri").setValue(newImageUri)
        }
    }


}