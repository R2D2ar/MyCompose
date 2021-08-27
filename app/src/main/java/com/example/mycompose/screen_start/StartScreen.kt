package com.example.mycompose.screen_start

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mycompose.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//Intro
@Composable
fun StartFragment(navController: NavController){
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.ic_icons8_rubber_duck_1),
            contentDescription = "Rubberduck",
            modifier = Modifier.size(width = 100.dp, height = 100.dp)
        )
    }

    SideEffect {
        coroutineScope.launch() {
            delay(1000)
            navController.navigate("login_register"){
                launchSingleTop = true

            }
        }
    }

}