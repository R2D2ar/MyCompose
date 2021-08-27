package com.example.mycompose.screen_register_login

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun LoginRegisterFragment(navController: NavHostController){
    navController.enableOnBackPressed(false)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Button(onClick = {
            navController.navigate("login")
        }) {
            Text(text = "Login")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            navController.navigate("register")
        }) {
            Text(text = "Register")
        }
    }
}