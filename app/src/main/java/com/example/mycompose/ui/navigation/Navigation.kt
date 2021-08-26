package com.example.mycompose.ui.navigation

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mycompose.main.MainPage
import com.example.mycompose.screen_login.LogInFragment
import com.example.mycompose.screen_register.RegisterFragment
import com.example.mycompose.screen_register_login.LoginRegisterFragment
import com.example.mycompose.screen_start.StartFragment

@Preview(showBackground = true)
@Composable
fun AppNavigator(){
    val controller = rememberNavController()


    NavHost(navController = controller, startDestination = "start"){
        composable("start"){ StartFragment(navController = controller) }
        composable("login_register"){ LoginRegisterFragment(navController = controller)}
        composable("login"){ LogInFragment(navController = controller)}
        composable("register"){ RegisterFragment(navController = controller)}
        composable("main"){ MainPage(navController = controller, backPressedDispatcher = OnBackPressedDispatcher()) }
    }
}