package com.example.compose.screens
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.R
import com.example.compose.components.AppToolbar
import com.example.compose.components.NavigationDrawerBody
import com.example.compose.components.NavigationDrawerHeader
import com.example.compose.data.home.HomeViewModel
import kotlinx.coroutines.launch
import java.lang.reflect.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(homeViewModel: HomeViewModel = viewModel()) {

    val drawerItem = listOf(
        DrawerItems(Icons.Default.Face, "Profile", 0, false),
        DrawerItems(Icons.Filled.Email, "Inbox", 32, true),
        DrawerItems(Icons.Filled.Favorite, "Favorite", 32, true),
        DrawerItems(Icons.Filled.Settings, "Setting", 0, false)
    )
    val drawerItem2 = listOf(
        DrawerItems(Icons.Default.Share, "Share", 0, false),
        DrawerItems(Icons.Filled.Star, "Rate", 0, false)
    )
    var selectedItem by remember { mutableStateOf(drawerItem[0]) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(drawerContent = {
        ModalDrawerSheet {

            Column(androidx.compose.ui.Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(
                    modifier =  androidx.compose.ui.Modifier.fillMaxWidth()
                        .height(200.dp)
                        .background(Color(0xffffc107)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        androidx.compose.ui.Modifier.wrapContentSize(),
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally
                    )
                    {
//                        homeViewModel.emailId.value
                        NavigationDrawerHeader("Nav Header")
//                        Image(
//                            painter = painterResource(id = R.drawable.message),
//                            contentDescription = "profile pic",
//                            modifier =  androidx.compose.ui.Modifier.size(130.dp)
//                                .clip(CircleShape)
//                        )
//                        Text(
//                            text = "Mr Shrek",
//                            androidx.compose.ui.Modifier.fillMaxWidth()
//                                .padding(top = 16.dp),
//                            fontSize = 22.sp,
//                            textAlign = TextAlign.Center
//                        )
                    }
                    Divider(
                        androidx.compose.ui.Modifier.align(Alignment.BottomCenter), thickness = 1.dp,
                        Color.DarkGray
                    )

                }
                NavigationDrawerBody(navigationDrawerItems = homeViewModel.navigationItemsList,
                onNavigationItemClicked = {
                    Log.d("ComingHere","inside_NavigationItemClicked")
                    Log.d("ComingHere","${it.itemId} ${it.title}")
                })






                Divider( thickness = 1.dp,
                    color = Color.DarkGray
                )
                drawerItem2.forEach{
                    NavigationDrawerItem(label = { Text(text = it.text) }
                        , selected = it == selectedItem
                        , onClick = {
                            selectedItem = it

                            scope.launch {
                                drawerState.close()
                            }

                        },
                        modifier= androidx.compose.ui.Modifier
                            .padding(horizontal = 16.dp)
                        , icon = {
                            Icon(imageVector = it.icon, contentDescription = it.text)
                        }
                    )
                }
            }
        }
    }, drawerState = drawerState) {

//        topBar = {
//            AppToolbar(toolbarTitle = stringResource(id = R.string.home),
//                logoutButtonClicked = {
//                    homeViewModel.logout()
//                },
//                navigationIconClicked = {
//                    coroutineScope.launch {
//                        //scaffoldState.drawerState.open()
//                    }
//                }
//            )
//        },

        Scaffold(

            topBar = {
//            TopAppBar(title = { Text(text = "drawer menu") },
            AppToolbar(toolbarTitle = stringResource(id = R.string.home),
                logoutButtonClicked = {
                    homeViewModel.logout()
                },
                navigationIconClicked = {

                        scope.launch {
                            drawerState.open()
                        }


//                    IconButton(onClick = {
//                        scope.launch {
//                            drawerState.open()
//                        }
//                    })
//                    {
//                        Icon(imageVector = Icons.Filled.Menu, contentDescription = "menu Icon")
//                    }

                }
            )
        }) { paddingValues ->
            Box(modifier= androidx.compose.ui.Modifier
                .fillMaxSize()
                .padding(paddingValues),
                contentAlignment = Alignment.Center){
                Button(onClick = {
                    scope.launch {
                        drawerState.open()
                    }
                }) {
                    Text(text = "Open drawer")
                }
            }
        }


    }































//   // val scaffoldState = rememberScaffoldState()
//    val coroutineScope = rememberCoroutineScope()
//
//    homeViewModel.getUserData()
//
//    Scaffold(
//
//        //scaffoldState = scaffoldState,
//        topBar = {
//            AppToolbar(toolbarTitle = stringResource(id = R.string.home),
//                logoutButtonClicked = {
//                    homeViewModel.logout()
//                },
//                navigationIconClicked = {
//                    coroutineScope.launch {
//                        //scaffoldState.drawerState.open()
//                    }
//                }
//            )
//        },
////        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
////        drawerContent = {
////            NavigationDrawerHeader(homeViewModel.emailId.value)
////            NavigationDrawerBody(navigationDrawerItems = homeViewModel.navigationItemsList,
////                onNavigationItemClicked = {
////                    Log.d("ComingHere","inside_NavigationItemClicked")
////                    Log.d("ComingHere","${it.itemId} ${it.title}")
////                })
////        }
//
//    ) { paddingValues ->
//        Surface(
//            modifier= androidx.compose.ui.Modifier
//                .fillMaxSize()
//                .background(Color.White)
//                .padding(paddingValues)
//
//        ) {
//            Column( modifier= androidx.compose.ui.Modifier
//                .fillMaxSize()) {
//
//                Text(text = "hello")
//            }
//
//        }
//    }
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