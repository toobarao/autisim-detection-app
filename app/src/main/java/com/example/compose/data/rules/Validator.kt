package com.example.compose.data.rules

import android.util.Patterns

object Validator {

    fun validateFirstName(fName:String):ValidationResult{
        return if(fName.isBlank()) {
            ValidationResult(
                successful = false,
                errorMessage = "This field can't be blank"
            )
        } else{
            ValidationResult(
                successful = true
            )
        }
    }
    fun validateLastName(lName:String):ValidationResult{
        return if(lName.isBlank()) {
            ValidationResult(
                successful = false,
                errorMessage = "This field can't be blank"
            )
        } else{
            ValidationResult(
                successful = true
            )
        }
    }
    fun validateEmail(email:String):ValidationResult{
        if(email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The email can't be blank"
            )
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = "That's not a valid email"
            )
        }
        return ValidationResult(
            successful = true
        )

    }
    fun validatePassword(password:String):ValidationResult{
        if(password.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to consist of at least 8 characters"
            )
        }
        val containsLettersAndDigits = password.any { it.isDigit() } &&
                password.any { it.isLetter() }
        if(!containsLettersAndDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to contain at least one letter and digit"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
    fun validatePrivacyPolicyAcceptance(statusValue:Boolean):ValidationResult{
        if(!statusValue) {
            return ValidationResult(
                successful = false,
                errorMessage = "Please accept the terms"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}
data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
   // val status:Boolean=false
)