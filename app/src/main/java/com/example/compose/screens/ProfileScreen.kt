package com.example.compose.screens

import android.net.Uri
import android.util.Log

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
import androidx.compose.material3.OutlinedTextField
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.compose.R
import com.example.compose.components.ButtonComponent
import com.example.compose.components.MyTextFieldComponent
import com.example.compose.components.NormalTextComponent
import com.example.compose.data.profile.UserProfileListener
import com.example.compose.data.profile.Users
import com.example.compose.data.profile.profileViewModel
import com.example.compose.navigation.SystemBackButtonHandler
import com.example.compose.ui.theme.colorPrimary
import com.example.compose.ui.theme.colorSecondary
import com.example.compose.utility.StoarageUtil
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(navController: NavController, profileViewModel: profileViewModel = viewModel()) {
    val TAG = com.example.compose.data.profile.profileViewModel::class.simpleName
    var name=profileViewModel.readDataFromSharedPreferences("name")
    var email=profileViewModel.readDataFromSharedPreferences("email")
    var edit:Boolean=false


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Surface(modifier= Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(8.dp)
            ) {

                ProfileImage(profileViewModel.readDataFromSharedPreferences("imageUri"))
                Spacer(modifier = Modifier.heightIn(70.dp))
                NormalTextComponent(value = "Name")
                Spacer(modifier = Modifier.heightIn(10.dp))
                OutlinedTextField(value =name, readOnly = edit, onValueChange = {name=it})
                NormalTextComponent(value = "Email")
                Spacer(modifier = Modifier.heightIn(10.dp))
                OutlinedTextField(value =email, readOnly = edit, onValueChange = {email=it})
                Spacer(modifier = Modifier.heightIn(40.dp))
                ButtonComponent(stringResource(id =  R.string.Edit), onButtonClicked = { edit=true
                    profileViewModel.writeToSharedPreferencesText(name,email)
                })
                if(edit)
                { ButtonComponent(stringResource(id =  R.string.save), onButtonClicked = {
                    profileViewModel.writeToSharedPreferencesText(name,email)
                     name=profileViewModel.readDataFromSharedPreferences("name")
                     email=profileViewModel.readDataFromSharedPreferences("email")
                    edit=false
                })}
            }}
    }

  //  }


}


@Composable
fun ProfileImage(imageUri:String?) {
val context= LocalContext.current

    var uri by rememberSaveable { mutableStateOf<Uri?>(imageUri?.toUri()) }

    val painter: Painter = if (uri == null || uri == Uri.EMPTY) {
        painterResource(R.drawable.profile) // Show default profile picture if URI is null or empty
    } else {
        rememberAsyncImagePainter(model = uri) // Show the image from URI
    }
    Image(
        painter= rememberAsyncImagePainter( "content://com.android.providers.media.documents/document/image%3A1000000034"),contentDescription = null
    )
//    val painter: Painter =
//        if (uri == null || uri == Uri.EMPTY) {
//            painterResource(R.drawable.profile) // Show default profile picture if URI is null or empty
//        } else {
//            rememberImagePainter(data = uri) { // Show the image from URI
//                // Handle loading and error states if needed
//            }
//        }

//    val painter: Painter =remember{
//        if (uri == null || uri == Uri.EMPTY) {
//            painterResource(R.drawable.profile) // Show default profile picture if URI is null or empty
//        } else {
//            rememberImagePainter(data = uri){
//
//            }
//           // rememberAsyncImagePainter(model =uri) // Show the image from URI
//        }}

    val singlePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uriResult -> uri = uriResult
            uri?.let {
                // Update Firebase Storage and Realtime Database
                StoarageUtil.uploadToStorage(uri = it, context = context, type = "image")

                // Update Realtime Database with the new image URI
               // profileViewModel.updateImageUriToDatabase(it.toString()) // Function to update URI in Realtime Database

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


//@Preview
//@Composable
//fun DefaultPreviewProfileScreen(){
//    ProfileScreen()
//}





