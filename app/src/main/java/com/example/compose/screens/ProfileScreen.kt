package com.example.compose.screens

import android.net.Uri

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.compose.R
import com.example.compose.components.ButtonComponent
import com.example.compose.components.MyTextFieldComponent
import com.example.compose.data.profile.UserProfileListener
import com.example.compose.data.profile.Users
import com.example.compose.data.profile.profileViewModel
import com.example.compose.ui.theme.colorPrimary
import com.example.compose.ui.theme.colorSecondary
import com.example.compose.utility.StoarageUtil
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(profileViewModel: profileViewModel = viewModel()) {

    var isLoading:Boolean=true

    val currentUser = FirebaseAuth.getInstance().currentUser
    val (userData, setUserData) = remember { mutableStateOf<Users?>(null) }
    val (errorState, setErrorState) = remember { mutableStateOf<String?>(null) }

    // Fetch user data when the screen is first composed
    LaunchedEffect(currentUser) {
        currentUser?.let { user ->
            profileViewModel.retrieveUserProfileData(user.uid, object : UserProfileListener {
                override fun onProfileDataReceived(userData: Users) {
                    isLoading=false
                    setUserData(userData) // Update the UI with fetched data
                }

                override fun onProfileError() {
                    isLoading=true
                    setErrorState("Error loading user data")

                    // Handle errors or null cases when retrieving user data
                }
            })
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Surface(modifier= Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)) {
            if (isLoading) {
                // Show a loading indicator
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(8.dp)
            ) {

                ProfileImage(profileViewModel,userData?.image)
                Spacer(modifier = Modifier.heightIn(70.dp))
                userData?.let { user ->
                    MyTextFieldComponent(labelValue = user.name, painterResource(id = R.drawable.profile), onTextSelected = {})
                    MyTextFieldComponent(user.email, painterResource(id = R.drawable.message), onTextSelected = {})

                    // Display other user information here
                } ?: run {
                    // You can show a loading indicator or handle cases where userData is null
                    Text(text = "Loading...")
                }


                Spacer(modifier = Modifier.heightIn(40.dp))
                ButtonComponent(stringResource(id =  R.string.update), onButtonClicked = { })
            }}
    }}


}


@Composable
fun ProfileImage(profileViewModel: profileViewModel = viewModel(),imageUri:String?) {
val context= LocalContext.current
    var uri by rememberSaveable { mutableStateOf<Uri?>(imageUri?.toUri()) }

    val painter: Painter =
        if (uri == null || uri == Uri.EMPTY) {
            painterResource(R.drawable.profile) // Show default profile picture if URI is null or empty
        } else {
            rememberAsyncImagePainter(model = uri) // Show the image from URI
        }

    val singlePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uriResult -> uri = uriResult
            uri?.let {
                // Update Firebase Storage and Realtime Database
                StoarageUtil.uploadToStorage(uri = it, context = context, type = "image")

                // Update Realtime Database with the new image URI
                profileViewModel.updateImageUriToDatabase(it.toString()) // Function to update URI in Realtime Database

            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(
                Brush.horizontalGradient(
                    listOf(colorPrimary, colorSecondary)
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Card(
            shape = CircleShape,
            modifier = Modifier
                .padding(8.dp)
                .size(100.dp)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        singlePhotoPicker.launch("image/*")
                    },
                painter =painter,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
        Text(text = "Change pic")
    }
}


@Preview
@Composable
fun DefaultPreviewProfileScreen(){
    ProfileScreen()
}





