package com.example.task_planner.presentation.screens.use_cases

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.task_planner.R

@Composable
fun ErrorUseCaseElement(error: String, onRetryClick: () -> Unit){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.size(64.dp),
            painter = painterResource(id = R.drawable.round_connection_failed),
            contentDescription = "connectionFailedIcon",
            colorFilter = ColorFilter.tint(color = Color.Red)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = error,
            color = Color.Red,
            textAlign = TextAlign.Center,
        )
//        ButtonElement(
//            text = "Попробовать снова",
//            modifier = Modifier.padding(all = 4.dp),
//            backgroundColor = MainTheme.colors.refusedColor,
//            onClick = {onRetryClick()}
//        )
    }
}