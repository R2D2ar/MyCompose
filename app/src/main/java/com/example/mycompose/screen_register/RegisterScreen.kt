package com.example.mycompose.screen_register

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mycompose.networking.getName
import com.example.mycompose.ui.common.CustomDialog
import io.ktor.network.sockets.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

//LoginFragment
@SuppressLint("ShowToast")
@Composable
fun RegisterFragment(navController: NavController, viewModel: RegisterViewModel = viewModel()) {
    navController.enableOnBackPressed(true)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    var email by remember {
        mutableStateOf("nichts")
    }
    var password by remember {
        mutableStateOf("null")
    }


    viewModel.connect()

    LaunchedEffect(key1 = true){
        scope.launch {
            viewModel.loginUiState.collect {
                 when(it){
                     is RegisterViewModel.LoginUiState.Success->{

                     }
                     is RegisterViewModel.LoginUiState.Error->{

                     }
                     else -> Unit
                 }
            }
        }
    }




    val openDialog = remember { mutableStateOf(false) }
    val dialogWidth = 200.dp
    val dialogHeight = 100.dp

    CustomDialog(state = openDialog, dialogWidth = dialogWidth, dialogHeight = dialogHeight)




    LaunchedEffect(true) {
        coroutineScope.launch {
            email = getName().toString()
            if (email == "null") {
                openDialog.value = true
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .padding(start = 70.dp, end = 70.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Register", fontSize = 30.sp,
                    fontFamily = FontFamily.Default,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 30.dp)
                )
            }

            Column(
                modifier = Modifier
                    .padding(bottom = 30.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = email, onValueChange = { email = it},
                    modifier = Modifier.padding(bottom = 20.dp)
                )
                TextField(
                    value = password, onValueChange = { password = it },
                    readOnly = false
                )
            }

            Column(horizontalAlignment = Alignment.End, modifier = Modifier.fillMaxWidth()) {
                Button(onClick = {
                    navController.navigate("main")

                }) {
                    Text(text = "Register")
                }
            }
        }
    }
}