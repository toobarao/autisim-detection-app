package com.example.compose.data.profile

import android.util.Log
import com.example.compose.utility.DataRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue

data class Users(
    val name:String,
    val email:String,
    var imageUri:String?

) {
    //constructor(name: String, email: String, imageUri: String?) : this(name, email, imageUri)
 constructor() : this("", "",null )

    //Note: this is needed to read the data from the firebase database
    //firebase database throws this exception: UserData does not define a no-argument constructor
    //
}
fun readingUserData(dataRepository: DataRepository){
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    val databaseReference = FirebaseDatabase.getInstance().reference.child("users")
    userId?.let { uid ->
        val userReference = databaseReference.child(uid)

        userReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    // User data exists, retrieve it
                    val userData = dataSnapshot.getValue<Users>()

                    dataRepository.writeToSharedPreferences(userData?.name,userData?.email,userData?.imageUri)
                   // Log.d(TAG, "Value is: " + userData?.name)
                    // Do something with userData
                } else {
                    // User data doesn't exist at this location
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
               // Log.w(TAG, "Failed to read value.", databaseError.toException())
                // Handle potential errors while retrieving data
            }
        })
    }

}
fun AddingUser(name: String, email: String) {
   // Log.d(TAG, "Initial Profile data")
   // Log.d(TAG, "Name" + name + "Email" + email)
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
