package com.example.mycompose.screen_main

import android.annotation.SuppressLint
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.runtime.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mycompose.ui.LocalBackPressedDispatcher
import com.example.mycompose.ui.theme.Orange500
import kotlinx.coroutines.launch

enum class AnimationState{
    START,
    STOP
}


//MainPage
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MainPage(navController: NavController, backPressedDispatcher: OnBackPressedDispatcher) {
    navController.enableOnBackPressed(false)
    var animationState: AnimationState
            by remember { mutableStateOf(AnimationState.STOP) }

    val scope = rememberCoroutineScope()
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
        topBar = {
            TopAppBar(modifier = Modifier
                .clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)),
                navigationIcon = {
                    Crossfade(targetState = animationState) {
                            currentstate ->
                        when(currentstate){
                            AnimationState.START ->
                                Icon(
                                    Icons.Default.Menu, null,
                                    modifier = Modifier
                                        .padding(start = 10.dp, end = 10.dp)
                                        .fillMaxSize()
                                        .clickable(onClick = {
                                            scope.launch {
                                                //scaffoldstate.drawerState.open()
                                                animationState = AnimationState.STOP

                                            }
                                        })
                                )
                            AnimationState.STOP ->
                                Icon(
                                    painter = painterResource(
                                        id = com.example.mycompose.R.drawable.ic_baseline_signal_cellular_connected_no_internet_4_bar_24), null,
                                        modifier = Modifier
                                        .padding(start = 10.dp, end = 10.dp)
                                        .fillMaxSize()
                                        .clickable(onClick = {
                                            scope.launch {
                                                //scaffoldstate.drawerState.open()
                                                animationState = AnimationState.START
                                            }
                                        })
                                )
                        }
                    }

                }, title = {Text("TopAppBar")},
                backgroundColor = Orange500)  },


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