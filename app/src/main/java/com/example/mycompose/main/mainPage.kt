package com.example.mycompose.main

import android.annotation.SuppressLint
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mycompose.LocalBackPressedDispatcher

//MainPage
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MainPage(navController: NavController, backPressedDispatcher: OnBackPressedDispatcher) {
    navController.enableOnBackPressed(false)

    val scaffoldstate = rememberScaffoldState()

    val backDispatcher = LocalBackPressedDispatcher.current
    val callback = remember{
        object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                navController.navigate("login_register")
            }
        }
    }

    DisposableEffect(key1 =  backDispatcher){
        backDispatcher.addCallback(callback)
        onDispose {
            callback.remove()
        }
    }

    Scaffold(
        scaffoldState = scaffoldstate,
        content = {MainContent()},
        drawerContent = { DrawerContent()}
    )




}

@Composable
fun MainContent(){
    val test by remember {
        mutableStateOf("")
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(text = test)
    }
}

@Composable
fun DrawerContent(){
    val scrollState = rememberScrollState()
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = "DrawerContent",
            fontSize = 30.sp,
            fontFamily = FontFamily.Default,
            color = Color.Black)
    }

}