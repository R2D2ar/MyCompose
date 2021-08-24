package com.example.mycompose

import android.annotation.SuppressLint
import android.graphics.drawable.shapes.Shape
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mycompose.networking.networking
import com.example.mycompose.ui.theme.MyComposeTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


val LocalBackPressedDispatcher =
    staticCompositionLocalOf<OnBackPressedDispatcher> { error("No Back Dispatcher provided") }

class MainActivity : ComponentActivity() {




    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyComposeTheme {
                CompositionLocalProvider(
                    LocalBackPressedDispatcher provides onBackPressedDispatcher,
                ) {
                    DefaultPreview()
                }
            }
        }
    }


}


@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppNavigator()
}



@Preview(showBackground = true)
@Composable
fun AppNavigator(){
    val controller = rememberNavController()


    NavHost(navController = controller, startDestination = "start"){
        composable("start"){ StartFragment(navController = controller)}
        composable("login_register"){ LoginRegisterFragment(navController = controller)}
        composable("login"){ LogInFragment(navController = controller)}
        composable("register"){ RegisterFragment(navController = controller)}
        composable("main"){ MainPage(navController = controller, backPressedDispatcher = OnBackPressedDispatcher())}
    }
}




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







//MainPage
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MainPage(navController: NavController, backPressedDispatcher: OnBackPressedDispatcher) {
    navController.enableOnBackPressed(false)

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


    var test by remember {
        mutableStateOf("")
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
       Text(text = test)
    }

}



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

//LoginFragment
@Composable
fun LogInFragment(navController: NavController){
    navController.enableOnBackPressed(true)
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    var email by remember {
        mutableStateOf("null")
    }
    var password by remember {
        mutableStateOf("null")
    }
    SideEffect {
        coroutineScope.launch {
            email = networking().toString()
            Log.d(email, "Test Test Test Test Test")
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(modifier = Modifier
            .padding(start = 70.dp, end = 70.dp)
            .fillMaxWidth()){
            Column(
                modifier = Modifier.fillMaxWidth()
            ){
                Text(text = "Login", fontSize = 30.sp,
                    fontFamily = FontFamily.Default,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 30.dp))
            }

            Column(
                modifier = Modifier
                    .padding(bottom = 30.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = email, onValueChange = {email = it},
                    modifier = Modifier.padding(bottom = 20.dp))
                TextField(value = password, onValueChange = {password = it},
                    readOnly = false)
            }
            
            Column(horizontalAlignment = Alignment.End, modifier = Modifier.fillMaxWidth()) {
                Button(onClick = {
                    navController.navigate("main")

                }) {
                    Text(text = "LogIn")
                }
            }
        }
    }
}



//LoginFragment
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun RegisterFragment(navController: NavController) {
    navController.enableOnBackPressed(true)

    val openDialog = remember { mutableStateOf(false) }
    val dialogWidth = 200.dp
    val dialogHeight = 100.dp

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    var email by remember {
        mutableStateOf("nichts")
    }
    var password by remember {
        mutableStateOf("null")
    }

    LaunchedEffect(true) {
        coroutineScope.launch {
            email = networking().toString()
            if (email == "null") {
                openDialog.value = true
            }
        }
    }



    if (openDialog.value) {
        Dialog(onDismissRequest = { openDialog.value = false }) {

            Box(
                Modifier
                    .size(dialogWidth, dialogHeight)
                    .clip(
                        RoundedCornerShape(10.dp)
                    )
                    .background(Color.White)
            ) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center)) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_signal_cellular_connected_no_internet_4_bar_24),
                        contentDescription = null
                    )
                }
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
                    value = email, onValueChange = { email = it },
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































