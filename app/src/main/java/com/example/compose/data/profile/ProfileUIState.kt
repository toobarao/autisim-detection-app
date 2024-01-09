package com.example.compose.data.profile

import android.net.Uri

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