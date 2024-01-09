package com.example.compose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.compose.R
import com.example.compose.components.HeadingTextComponent
import com.example.compose.components.NormalTextComponent
import com.example.compose.data.login.loginViewModel

@Composable
fun AboutScreen(navController: NavController){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Surface(modifier= Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)) {

            Column(modifier = Modifier.fillMaxSize())
            {
                HeadingTextComponent(value = stringResource(id = R.string.about))
                Spacer(modifier = Modifier.height(20.dp))
                NormalTextComponent("skeifejufioefieufeufeifueifuefeiu")




            }
        }
    }
}
//@Preview
//@Composable
//fun DefaultPreviewAboutScreen(){
//    AboutScreen()
//}