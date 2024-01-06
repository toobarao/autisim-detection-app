package com.example.compose.data.profile

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.data.signup.SignupViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class profileViewModel: ViewModel() {
    val auth = FirebaseAuth.getInstance()

    private val TAG = profileViewModel::class.simpleName
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
                image = null
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
            userDataUpdates["image"] = imageUrl

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




    fun retrieveUserProfileData(userId: String, callback: UserProfileListener) {
        val userIdReference = Firebase.database.reference
            .child("users").child(userId)

        userIdReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val userData = dataSnapshot.getValue<Users>()
                    userData?.let {
                        callback.onProfileDataReceived(it) // Pass the user data
                    } ?: run {
                        callback.onProfileError() // Handle the case where user data is null
                    }
                } else {
                    callback.onProfileError() // Handle the case where user data doesn't exist
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors that may occur during data retrieval
                callback.onProfileError()
            }
        })
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