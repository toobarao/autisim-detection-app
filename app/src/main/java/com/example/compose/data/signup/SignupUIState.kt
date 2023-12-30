package com.example.compose.data.signup

data class SignupUIState (
    var firstName:String="",
    var lastName:String="",
    var email:String="",
    var password:String="",
    var privacyPolicyAccepted :Boolean = false,

//    var firstNameError:Boolean=false,
    var firstNameError: String?=null,
    var lastNameError:String?=null,
    var emailError:String?=null,
    var passwordError:String?=null,
    var privacyPolicyError:String?=null
)
