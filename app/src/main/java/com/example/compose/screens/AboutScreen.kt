package com.example.compose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

            LazyColumn(modifier = Modifier.fillMaxSize())
            {
                item {  HeadingTextComponent(value = stringResource(id = R.string.about))
                Spacer(modifier = Modifier.height(20.dp))
                Text(text="Purpose Of Our App:",
                    modifier= Modifier
                        .fillMaxWidth()
                        .heightIn(),
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal),
                    color= colorResource(id = R.color.colorPrimary)
                )
                Text(modifier = Modifier.fillMaxWidth().heightIn(40.dp),
                    text="To design a system" +
                        " that is based on autism spectrum disorder detection on social media " +
                        "and face recognition.This study demonstrated the use of a well-trained " +
                        "classification model (based on transfer learning) to detect autism from an " +
                        "image of a child. With the advent of high-specification mobile devices, this model" +
                        " can readily provide a diagnostic test of putative autistic traits by taking an image" +
                        " with cameras.",
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal)
                            )}
                item {
                Spacer(modifier = Modifier.height(20.dp))
                Text(text="Point To Note:",
                    modifier= Modifier
                        .fillMaxWidth()
                        .heightIn(),
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal),
                    color= colorResource(id = R.color.colorPrimary)
                )
                Text(modifier = Modifier.fillMaxWidth().heightIn(40.dp),
                    text="→ Children with ASD have dissimilar facial landmarks, which set them noticeably apart from typically developed (TD) children. \n" +
                            "→ The earliest symptoms of ASD often appear within the first year of life and may include lack of eye contact, lack of response to name calling, and indifference to caregivers.\n" +
                            "→ facial recognition plays a more significant role in detecting autism than the person's emotional state.\n",
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal))
                Spacer(modifier = Modifier.height(20.dp))}
                item{
                Text(text="Deep Learning Techniques:",
                    modifier= Modifier
                        .fillMaxWidth()
                        .heightIn(),
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal),
                    color= colorResource(id = R.color.colorPrimary)
                )
                Text(modifier = Modifier.fillMaxWidth().heightIn(40.dp),
                    text="Convolutional neural network with transfer learning and the flask framework. Xception, Visual Geometry Group Network (VGG19), and NASNETMobile are the pre-trained models that were used for the classification task. \n",
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal))}



            }
        }
    }
}
//@Preview
//@Composable
//fun DefaultPreviewAboutScreen(){
//    AboutScreen()
//}