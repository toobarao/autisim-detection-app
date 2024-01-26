package com.example.compose.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset

import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton


import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import com.example.compose.R
import com.example.compose.components.ButtonComponent
import com.example.compose.components.ClickableLoginTextComponent


import com.example.compose.navigation.Screen
import kotlinx.coroutines.launch




@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(navController: NavController){
    val childPics = listOf(
        R.drawable.medium_shot_child_wearing_headphones,
        R.drawable.medium_shot_boy_with_laptop,
        R.drawable.medium_shot_kid_wearing_headphones,
    )
    val pagerState = rememberPagerState(pageCount = {
        3
    })

   val scope = rememberCoroutineScope()

//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {

        Surface(modifier= Modifier
            .fillMaxSize()
            //.background(Color.White)
            .padding(28.dp)) {

            Column()
            {
                Image(modifier=Modifier.align(Alignment.CenterHorizontally),
                    painter = painterResource(R.drawable.sun_31_12_2023_18_41_37),
                    contentDescription = null,

                )
                Spacer(modifier = Modifier.height(20.dp))

                Box(modifier=Modifier.height(500.dp)) {


                    HorizontalPager(

                        state = pagerState,
                        key = { childPics[it] },
                        //pageSize = childPics.F
                    ) { index ->
                        Image(
                            painter = painterResource(id = childPics[index]),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                           // modifier = Modifier.fillMaxSize()
                        )
                    }
                    Box(
                        modifier = Modifier
                            .offset(y = -(16).dp)
                            .fillMaxWidth(0.5f)
                            .clip(RoundedCornerShape(100))
                            .background(Color.White)
                            .padding(3.dp)
                            .align(Alignment.BottomCenter)
                    ) {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(
                                        pagerState.currentPage - 1
                                    )
                                }
                            },
                            modifier = Modifier.align(Alignment.CenterStart)
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowLeft,
                                contentDescription = "Go back"
                            )
                        }
                        IconButton(
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(
                                        pagerState.currentPage + 1
                                    )
                                }
                            },
                            modifier = Modifier.align(Alignment.CenterEnd)
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = "Go forward"
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                ButtonComponent(stringResource(id =  R.string.register), onButtonClicked = {
                    navController.navigate(Screen.SignUpScreen.route)
//                    PostOfficeAppRouter.navigateTo(
//                        Screen.SignUpScreen)
                }
                )
                Spacer(modifier = Modifier.height(20.dp))
                ClickableLoginTextComponent(tryingToLogin = true, onTextSelected = {
                    navController.navigate(Screen.LoginScreen.route)
//                    PostOfficeAppRouter.navigateTo(
//                    Screen.LoginScreen)

                })

            }

        }

   // }





}
//@Preview
//@Composable
//fun DefaultPreviewMainScreen(){
//    MainScreen(navController = NavController)
//}



