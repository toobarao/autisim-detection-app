package com.example.compose.screens

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.compose.R
import com.example.compose.components.AppToolbar
import com.example.compose.components.NavigationDrawerBody
import com.example.compose.components.NormalTextComponent
import com.example.compose.data.home.HomeViewModel
import com.example.compose.ui.theme.colorPrimary
import com.example.compose.ui.theme.colorSecondary
import com.example.compose.utility.StoarageUtil
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(homeViewModel: HomeViewModel = viewModel()) {

    val drawerItem2 = listOf(
        DrawerItems(Icons.Default.Share, "Share", 0, false),
        DrawerItems(Icons.Filled.Star, "Rate", 0, false)
    )
    var selectedItem by remember { mutableStateOf(drawerItem2[0]) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()



    ModalNavigationDrawer(drawerContent = {
        ModalDrawerSheet {

            Column(androidx.compose.ui.Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(
                    modifier = androidx.compose.ui.Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(
                            Brush.horizontalGradient(
                                listOf(colorPrimary, colorSecondary)
                            )
                        ),
                   // contentAlignment = Alignment.Center
                ) {
                    Column(
                        androidx.compose.ui.Modifier.fillMaxSize(),
                            //.wrapContentSize(),
                      // verticalArrangement = Arrangement.SpaceAround,
                        //horizontalAlignment = Alignment.CenterHorizontally
                    )
                    {

                       

                        IconButton(modifier = androidx.compose.ui.Modifier.align(Alignment.End),onClick = {
                            scope.launch {
                                drawerState.close()
                            }
                        })
                        {
                            Icon(imageVector = Icons.Filled.Close, contentDescription = "close Icon")
                        }
                        Spacer(modifier = androidx.compose.ui.Modifier.height(20.dp))
                        Image(
                            modifier = androidx.compose.ui.Modifier.align(Alignment.CenterHorizontally),
                            painter = painterResource(R.drawable.sun_31_12_2023_18_41_37),
                            contentDescription = null,

                            )

//                        homeViewModel.emailId.value



                    }

                    Divider(
                        androidx.compose.ui.Modifier
                            .align(Alignment.BottomEnd)
                            .padding(3.dp),thickness = 1.dp,
                        Color.DarkGray
                    )
                }

                NavigationDrawerBody(navigationDrawerItems = homeViewModel.navigationItemsList,
                onNavigationItemClicked = {

                    Log.d("ComingHere","inside_NavigationItemClicked")
                    Log.d("ComingHere","${it.itemId} ${it.title}")
                })

                Spacer(modifier = androidx.compose.ui.Modifier.height(50.dp))


                Divider( modifier= androidx.compose.ui.Modifier.padding(10.dp),thickness = 1.dp,
                    color = Color.DarkGray
                )


                drawerItem2.forEach{
                    NavigationDrawerItem(label = { Text(text = it.text, fontSize = 15.sp) }
                        , selected = it == selectedItem
                        , onClick = {
                            //selectedItem = it
                        },
                        colors= NavigationDrawerItemDefaults.colors(
                            selectedContainerColor =Color.LightGray,
                        ),
                        modifier= androidx.compose.ui.Modifier
                            .padding(horizontal = 12.dp)
                        , icon = {
                            Icon(imageVector = it.icon, contentDescription = it.text)
                        }
                    )
                }
            }
        }
    }, drawerState = drawerState)


    {
        Scaffold(

            topBar = {
                AppToolbar(toolbarTitle = stringResource(id = R.string.home),
                    logoutButtonClicked = {
                        homeViewModel.logout()
                    },
                    navigationIconClicked = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            }) { paddingValues ->


            val context = LocalContext.current

            var imageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }

            val multiplePhotoPicker = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickMultipleVisualMedia(),
                onResult = {
                    imageUris = it
                }
            )

            Column(
                modifier = Modifier.padding(paddingValues).padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = androidx.compose.ui.Modifier.height(40.dp))
                NormalTextComponent(value = "Upload your child images and with in just 2-3 minutes test we should be able to help you if your child might be autistic")

                LazyColumn {
                    item {
                        Button(modifier = Modifier
                            .fillMaxSize()
                            .fillMaxWidth()
                            .heightIn(48.dp),
                            colors = ButtonDefaults.buttonColors(Color.Transparent),
                            // shape = RoundedCornerShape(50.dp),
                            onClick = {
                                multiplePhotoPicker.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            }) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(20.dp)
                                    .heightIn(48.dp)
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            listOf(
                                                colorSecondary,
                                                colorPrimary
                                            )
                                        ),
                                        shape = RoundedCornerShape(50.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Pick Multiple Images",style = TextStyle(fontSize = 18.sp))
                            }

                        }
                    }

                    items(imageUris) { uri ->
                        AsyncImage(
                            model = uri,
                            contentDescription = null,
                            modifier = Modifier.size(248.dp)
                        )

                    }

                }

                Button(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                    colors = ButtonDefaults.buttonColors(Color.Transparent), onClick = {
                        imageUris.forEach { uri ->

                            uri?.let {
                                StoarageUtil.uploadToStorage(
                                    uri = it,
                                    context = context,
                                    type = "image"
                                )

                            }
                        }

                    }) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                            .heightIn(48.dp)
                            .background(
                                brush = Brush.horizontalGradient(
                                    listOf(
                                        colorSecondary,
                                        colorPrimary
                                    )
                                ),
                                shape = RoundedCornerShape(50.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text( "Upload",style = TextStyle(fontSize = 18.sp))
                    }
                }

            }

        }


    }

}


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

data class DrawerItems(

    val icon : ImageVector,
    val text : String,
    val badgeCount : Int,
    val hasBadge : Boolean
)