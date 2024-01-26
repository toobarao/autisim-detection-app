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
import androidx.compose.material3.Surface
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.compose.R
import com.example.compose.components.NavigationDrawerBody
import com.example.compose.navigation.navigationItemsList
import com.example.compose.ui.theme.colorPrimary
import com.example.compose.ui.theme.colorSecondary
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermsAndConditionsScreen(navController: NavController){
    val item1= navigationItemsList
   // val item2= extraItems
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

                        //   homeViewModel.emailId.value



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
                        text = "Privacy Policy", color = Color.White
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
                    .fillMaxWidth()
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                termsAndCond()


            }}
    }

    }
@Composable
fun termsAndCond(){

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(40.dp),
                text = "Privacy",

                style = TextStyle(
                    fontSize = 24.sp, fontWeight = FontWeight.Bold,
                    color = colorPrimary,
                    fontStyle = FontStyle.Normal
                )
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(40.dp),
                text = "Privacy Statement",

                style = TextStyle(
                    fontSize = 20.sp, fontWeight = FontWeight.Bold,

                    fontStyle = FontStyle.Normal
                )
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(40.dp),
                text = "Privacy of your personal information is important to us and" +
                        " we conduct our business with respect and integrity " +
                        "We comply with information privacy principles of the Privacy and " +
                        "Data Protection Act 2014 (Vic) and the Health Records Act 2001 (Vic)\n.",
                style = TextStyle(
                    fontSize = 18.sp, fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal
                )
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(40.dp),
                text = "Term Of Use",

                style = TextStyle(
                    fontSize = 20.sp, fontWeight = FontWeight.Bold,

                    fontStyle = FontStyle.Normal
                )
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(40.dp),
                text = "You must follow any policies made available\n" +
                        "to you within the application, The\n" +
                        "application was launched to benefit the\n" +
                        "community by empowering parents to assess\n" +
                        "the Social attention and communication\n" +
                        "behaviours of their children younger than\n" +
                        "2 years (between 11 and 30 months).\n",
                style = TextStyle(
                    fontSize = 18.sp, fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal
                )
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(40.dp),
                text = "Please do not misuse our Services, For\n" +
                        "example, don't interfere with our Services\n" +
                        "or try to access them usin9 a method other\n" +
                        "than the interface and the instructions\n" +
                        "that we provide, We may Suspend or cease\n" +
                        "providing our Services to you if you do not\n" +
                        "comply with our terms or policies.\n",
                style = TextStyle(
                    fontSize = 18.sp, fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal
                )
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(40.dp),
                text = "By using our services you also agree not\n" +
                        "to& (1) use or attempt to use another\n" +
                        "user's account without authorisation,\n" +
                        "or impersonate any person or entity; (2)\n" +
                        "conduct an assessment for a child without\n" +
                        "legal rights or without parents' consent\n" +
                        "(3) violate, or encourage any conduct\n" +
                        "that would violate, any applicable law or\n" +
                        "regulation or would give rise to civil liability;\n" +
                        "(4) be fraudulent, false, misleading, or\n" +
                        "deceptive; and (5) use the application in\n" +
                        "any manner that, in our Sole discretion, is\n" +
                        "objectionable or restricts or inhibits any\n" +
                        "other person from using the application\n" +
                        "or which may expose us or our users to\n" +
                        "harm or liability of any type.",
                style = TextStyle(
                    fontSize = 18.sp, fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal
                )
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(40.dp),
                text = "Using our Services does not give you\n" +
                        "ownership of any intellectual property\n" +
                        "rights to our application or the content you\n" +
                        "access, You may not use content from our\n" +
                        "Services unless you obtain permission from\n" +
                        "La Trobe University or otherwise permitted\n" +
                        "by law, These terms do not grant you the\n" +
                        "right to use any branding or logos used in\n" +
                        "our Services\n",
                style = TextStyle(
                    fontSize = 18.sp, fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal
                )
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(40.dp),
                text = "n connection with your use of the\n" +
                        "Services, we may send you emails relating\n" +
                        "to your assessments, account and other\n" +
                        "information, You may opt-out of Some of\n" +
                        "those communications but remember some\n" +
                        "of these communications are important\n" +
                        "for you to receive such as receiving your\n" +
                        "assessment reSults,",
                style = TextStyle(
                    fontSize = 18.sp, fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal
                )
            )



}


//@Preview
//@Composable
//fun TermsAndConditionsScreenPreview(){
//    termsAndCond()
//}