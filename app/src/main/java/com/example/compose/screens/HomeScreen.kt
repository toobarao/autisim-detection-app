package com.example.compose.screens

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.compose.R
import com.example.compose.components.AppToolbar
import com.example.compose.components.NavigationDrawerBody
import com.example.compose.components.NormalTextComponent
import com.example.compose.data.home.HomeUIState
import com.example.compose.data.home.HomeViewModel
import com.example.compose.data.profile.Users
import com.example.compose.data.profile.profileViewModel
import com.example.compose.ml.Vgg16WoDa89
import com.example.compose.navigation.extraItems
import com.example.compose.navigation.navigationItemsList
import com.example.compose.ui.theme.colorPrimary
import com.example.compose.ui.theme.colorSecondary
import com.example.compose.utility.StoarageUtil
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer


fun uriToBitmap(contentResolver: ContentResolver, uri: Uri?): Bitmap? {
    if (uri == null) return null

    return try {
        val inputStream = contentResolver.openInputStream(uri)
        BitmapFactory.decodeStream(inputStream)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

private suspend fun performInference(model: Vgg16WoDa89, bitmap: Bitmap?): String {
    return bitmap?.let {
        withContext(Dispatchers.Default) {
            val imageProcessor = ImageProcessor.Builder()
                .add(NormalizeOp(0.0f, 255.0f))
                .add(ResizeOp(255, 255, ResizeOp.ResizeMethod.BILINEAR))
                .build()

            var tensorImage = TensorImage(DataType.FLOAT32)
            tensorImage.load(it)
            tensorImage = imageProcessor.process(tensorImage)

            val inputFeature0 =
                TensorBuffer.createFixedSize(intArrayOf(1, 255, 255, 3), DataType.FLOAT32)
            inputFeature0.loadBuffer(tensorImage.buffer)
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray
            model.close()
            Log.d("my tag", "array size" + outputFeature0.size)
            var result = outputFeature0[0] * 100
            if (result < 50)
                return@withContext "The model suggests $result% a lower probability of autism. For accurate results, consider consulting with a healthcare professional."
            else
                return@withContext "The model indicates $result% probability of autism. It's important to consult with a healthcare professional for a comprehensive evaluation."
        }
    } ?: throw IllegalArgumentException("Input bitmap is null.")
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, homeViewModel: HomeViewModel = viewModel()) {
    // val TAG = profileViewModel::class.simpleName
    val context= LocalContext.current
    val model = Vgg16WoDa89.newInstance(context)
    var isLoading by remember { mutableStateOf(false) }
    var outputText by remember { mutableStateOf("") }
    val contentResolver = context.contentResolver
    var bitmap: Bitmap? = null


    val item1= navigationItemsList
    val item2=extraItems

    var uri by remember{
        mutableStateOf<Uri?>(null)
    }

    val singlePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            uri = it
        }
    )
    var selectedItem  by rememberSaveable { mutableStateOf(0) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val profileViewModel:profileViewModel= viewModel()
    profileViewModel.readingUserData()
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
//                    Log.d("ComingHere","inside_NavigationItemClicked")
//                    Log.d("ComingHere","${it.itemId} ${it.title}")
                })

                Spacer(modifier = Modifier.height(50.dp))


                Divider( modifier= Modifier.padding(10.dp),thickness = 1.dp,
                    color = Color.DarkGray
                )


                item2.forEachIndexed{index, item ->
                    NavigationDrawerItem(label = { Text(text = item.text, fontSize = 15.sp) }
                        , selected = index == selectedItem
                        , onClick = { selectedItem = index
                            scope.launch {
                                drawerState.close()
                            }},
                        colors= NavigationDrawerItemDefaults.colors(
                            selectedContainerColor =Color.LightGray,
                        ),
                        modifier= Modifier
                            .padding(horizontal = 12.dp)
                        , icon = {
                            Icon(imageVector = item.icon, contentDescription = item.text)
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
                        homeViewModel.logout(navController)
                    },
                    navigationIconClicked = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            }) { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Spacer(modifier = Modifier.height(40.dp))
                NormalTextComponent(value = "Upload your child image and with in just 2-3 minutes test we should be able to help you if your child might be autistic")

                        Button(modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(48.dp),
                            colors = ButtonDefaults.buttonColors(Color.Transparent),
                            // shape = RoundedCornerShape(50.dp),
                            onClick = {
                                singlePhotoPicker.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            }) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
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
                                Text("Pick Single Image",style = TextStyle(fontSize = 18.sp))
                            }

                      //  }
                    }
                if (isLoading) {
                    CircularProgressIndicator() // Your loader component, you can customize it according to your needs
                } else {

                    AsyncImage(model = uri, contentDescription = null, modifier = Modifier.size(248.dp))

                Button(modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(48.dp),
                    colors = ButtonDefaults.buttonColors(Color.Transparent), onClick = {
                            uri?.let{
                                bitmap=uriToBitmap(contentResolver, uri)
                                isLoading = true
                                GlobalScope.launch {
                                    outputText=performInference(model, bitmap)
                                    isLoading = false
                                }
                                
                                //StoarageUtil.uploadToStorage(uri=it, context=context, type="image")
                            }


                    }) {
                    Box(

                        modifier = Modifier
                            .fillMaxWidth()
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
                        contentAlignment = Alignment.Center,

                    ) {
                        Text( "Upload",style = TextStyle(fontSize = 18.sp))
                    }

                }
                }
                LaunchedEffect(outputText) {


                    // This block runs when outputText changes
                    // Update UI or process the result here
                    // For example, if you want to log the result, you can use Log.d
                     Log.d("my tag", "Updated outputText: $outputText")

                }
                NormalTextComponent(value = outputText)




            }

        }


    }

}


//@Preview
//@Composable
//fun HomeScreenPreview() {
//    HomeScreen()
//}

