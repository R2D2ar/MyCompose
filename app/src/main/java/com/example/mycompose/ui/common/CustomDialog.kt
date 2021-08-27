package com.example.mycompose.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.mycompose.R

@Composable
fun CustomDialog(state:MutableState<Boolean>, dialogWidth: Dp, dialogHeight:Dp){
    if (state.value) {
        Dialog(onDismissRequest = { state.value = false }) {

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
                    Text(text = "Error", Modifier.padding(top = 30.dp))
                }
            }
        }
    }
}