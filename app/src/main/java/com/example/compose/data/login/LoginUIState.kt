package com.example.compose.data.login

data class loginUIState(

    var email:String="",
    var password:String="",

    var emailError:String?=null,
    var passwordError:String?=null,
    var loginErrorMessage:String?=null,


)
