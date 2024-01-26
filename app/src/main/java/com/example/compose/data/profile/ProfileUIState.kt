package com.example.compose.data.profile

data class profileUIState(

    var email:String="",
    var name:String="",

    var emailError:String?=null,
    var nameError:String?=null,
    var profileErrorMessage:String?=null,


    )