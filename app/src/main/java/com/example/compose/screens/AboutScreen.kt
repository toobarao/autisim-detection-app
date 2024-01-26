package com.example.compose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.compose.R
import com.example.compose.components.HeadingTextComponent
import com.example.compose.components.NavigationDrawerBody
import com.example.compose.navigation.navigationItemsList
import com.example.compose.ui.theme.colorPrimary
import com.example.compose.ui.theme.colorSecondary
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavController){
    val item1= navigationItemsList

    var selectedItem  by rememberSaveable { mutableStateOf(0) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(drawerContent = {
        ModalDrawerSheet {
            Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(
                            Brush.horizontalGradient(
                                listOf(colorPrimary, colorSecondary)
                            )
                        ),

                    ) {
                    Column(Modifier.fillMaxSize(),)
                    {
                        IconButton(modifier = Modifier.align(Alignment.End),onClick = {
                            scope.launch {
                                drawerState.close()
                            }
                        })
                        {
                            Icon(imageVector = Icons.Filled.Close, contentDescription = "close Icon")
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Image(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            painter = painterResource(R.drawable.sun_31_12_2023_18_41_37),
                            contentDescription = null,

                            )



                    }
                    Divider(
                        Modifier
                            .align(Alignment.BottomEnd)
                            .padding(3.dp),thickness = 1.dp, Color.DarkGray)
                }

                NavigationDrawerBody(navigationDrawerItems = item1,
                    onNavigationItemClicked = {it->
                        navController.navigate(it.route)
                        scope.launch {
                            drawerState.close()
                        }
//                    Log.d("ComingHere","inside_NavigationItemClicked")
//                    Log.d("ComingHere","${it.itemId} ${it.title}")
                    })

                Spacer(modifier = Modifier.height(50.dp))


                Divider( modifier= Modifier.padding(10.dp),thickness = 1.dp,
                    color = Color.DarkGray
                )



            }
        }
    }, drawerState = drawerState){
        Scaffold(

            topBar = { TopAppBar(
                title = {
                    Text(
                        text = "About", color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorPrimary,
                    titleContentColor = colorSecondary,
                ),

                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = stringResource(R.string.menu),
                            tint = Color.White
                        )
                    }

                },

                )
            }){
                paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                    HeadingTextComponent(value = stringResource(id = R.string.about))
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Purpose Of Our App:",
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(),
                        style = TextStyle(
                            fontSize = 20.sp, fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Normal
                        ),
                        color = colorResource(id = R.color.colorPrimary)
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(40.dp),
                        text = "To design a system" +
                                " that is based on autism spectrum disorder detection on social media " +
                                "and face recognition.This study demonstrated the use of a well-trained " +
                                "classification model (based on transfer learning) to detect autism from an " +
                                "image of a child. With the advent of high-specification mobile devices, this model" +
                                " can readily provide a diagnostic test of putative autistic traits by taking an image" +
                                " with cameras.",
                        style = TextStyle(
                            fontSize = 18.sp, fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Normal
                        )
                    )


                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Point To Note:",
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(),
                        style = TextStyle(
                            fontSize = 20.sp, fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Normal
                        ),
                        color = colorResource(id = R.color.colorPrimary)
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(40.dp),
                        text = "→ Children with ASD have dissimilar facial landmarks, which set them noticeably apart from typically developed (TD) children. \n" +
                                "→ The earliest symptoms of ASD often appear within the first year of life and may include lack of eye contact, lack of response to name calling, and indifference to caregivers.\n" +
                                "→ facial recognition plays a more significant role in detecting autism than the person's emotional state.\n",
                        style = TextStyle(
                            fontSize = 18.sp, fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Normal
                        )
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Deep Learning Techniques:",
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(),
                        style = TextStyle(
                            fontSize = 20.sp, fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Normal
                        ),
                        color = colorResource(id = R.color.colorPrimary)
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(40.dp),
                        text = "Convolutional neural network with transfer learning and the flask framework. Xception, Visual Geometry Group Network (VGG19), and NASNETMobile are the pre-trained models that were used for the classification task. \n",
                        style = TextStyle(
                            fontSize = 18.sp, fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Normal
                        )
                    )



            }

            }}}





