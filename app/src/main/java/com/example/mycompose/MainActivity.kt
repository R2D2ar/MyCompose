package com.example.mycompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.tooling.preview.Preview
import com.example.mycompose.ui.navigation.AppNavigator
import com.example.mycompose.ui.theme.MyComposeTheme


val LocalBackPressedDispatcher =
    staticCompositionLocalOf<OnBackPressedDispatcher> { error("No Back Dispatcher provided") }


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyComposeTheme {
                CompositionLocalProvider(
                    LocalBackPressedDispatcher provides onBackPressedDispatcher,
                ) {
                    AppNavigator()
                }
            }
        }
    }


}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppNavigator()
}




































