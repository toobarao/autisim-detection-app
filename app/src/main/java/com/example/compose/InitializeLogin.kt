package com.example.compose

import android.app.Application
import com.google.firebase.FirebaseApp

class InitializeLogin: Application() {
    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)
    }
}