package com.example.compose.data.profile


sealed class profileUIEvent {

    data class EmailChanged(val email:String): profileUIEvent()
    data class NameChanged(val Name:String): profileUIEvent()
    object SaveButtonClicked : profileUIEvent()

}