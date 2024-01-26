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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.compose.R
import com.example.compose.components.ButtonComponent
import com.example.compose.components.NavigationDrawerBody
import com.example.compose.components.errorMessage
import com.example.compose.data.profile.profileUIEvent
import com.example.compose.data.profile.profileViewModel
import com.example.compose.navigation.navigationItemsList
import com.example.compose.ui.theme.colorPrimary
import com.example.compose.ui.theme.colorSecondary
import com.example.compose.utility.StoarageUtil
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    val TAG = com.example.compose.data.profile.profileViewModel::class.simpleName
    val item1= navigationItemsList
  //  val item2= extraItems
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
                        text = "Profile", color = Color.White
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

                )}){
                paddingValues ->

            Column(
                modifier = Modifier.fillMaxWidth()
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val context = LocalContext.current

                // Create an instance of DataRepository
               // val repository = remember { DataRepository(context as Application) }
                ProfileContent()


            }}}




}


@Composable
fun ProfileImage(profileViewModel: profileViewModel = viewModel(),imageUri:String?) {
val context= LocalContext.current
    var uri by rememberSaveable { mutableStateOf<Uri?>(imageUri?.toUri()) }
    val painter: Painter = if (uri == null || uri == Uri.EMPTY) {
        painterResource(R.drawable.profile) // Show default profile picture if URI is null or empty
    } else {
        rememberAsyncImagePainter(model = uri) // Show the image from URI
    }

    val singlePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uriResult -> uri = uriResult
            uri?.let {uri->
                StoarageUtil.uploadToStorage(uri = uri, context = context, type = "image") {
                    imageUrl ->
                    profileViewModel.updateImageUriToDatabase(imageUrl)

                }

            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
        ,
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



@Composable
fun ProfileContent(profileViewModel: profileViewModel = viewModel()){
    val TAG = com.example.compose.data.profile.profileViewModel::class.simpleName



    val name = profileViewModel.readProfileData("name")
    val email = profileViewModel.readProfileData("email")
    var edit by remember { mutableStateOf(false) }
    var tname by remember { mutableStateOf(name) }
    var temail by remember { mutableStateOf(email) }
    ProfileImage(profileViewModel,profileViewModel.readProfileData("imageUri"))
    Spacer(modifier = Modifier.heightIn(2.dp))
    Text("Name",modifier= Modifier
        .fillMaxWidth()
        .heightIn(min = 10.dp)
        .padding(start = 47.dp))
    Spacer(modifier = Modifier.heightIn(5.dp))
    OutlinedTextField(value =tname, readOnly = !edit,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1,
        onValueChange = {
            tname=it
            profileViewModel.onEvent(
                profileUIEvent.NameChanged(tname))},
        leadingIcon = { Icon(painter= painterResource(id = R.drawable.profile), contentDescription = "") })

    if (profileViewModel.profileUIState.value.nameError != null) {
        errorMessage(errorMessage = profileViewModel.profileUIState.value.nameError.toString())
    }
    Spacer(modifier = Modifier.heightIn(10.dp))
    Text("Email",modifier= Modifier
        .fillMaxWidth()
        .heightIn(min = 10.dp)
        .padding(start = 47.dp))
    Spacer(modifier = Modifier.heightIn(5.dp))
    OutlinedTextField(value =temail, readOnly =!edit,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        singleLine = true,
        maxLines = 1,
        onValueChange = {
            temail=it
            profileViewModel.onEvent(
                profileUIEvent.EmailChanged(temail))},
        leadingIcon = { Icon(painter= painterResource(id = R.drawable.message), contentDescription = "") }
    )
    if (profileViewModel.profileUIState.value.emailError != null) {
        errorMessage(errorMessage = profileViewModel.profileUIState.value.emailError.toString())
    }

    Spacer(modifier = Modifier.heightIn(40.dp))
    ButtonComponent(stringResource(id =  R.string.Edit), onButtonClicked = { edit=!edit

    })
    Spacer(modifier = Modifier.heightIn(40.dp))
    if(edit){
        ButtonComponent(stringResource(id =  R.string.save), onButtonClicked = {
            profileViewModel.writeUserData(tname,temail)
            profileViewModel.onEvent(
                profileUIEvent.SaveButtonClicked)
            edit=!edit
        })}


}
//@Preview
//@Composable
//fun DefaultPreviewProfileScreen(){
//    ProfileName()
//}





