package com.example.task_planner.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun TaskDoneScreenList(){
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "TASK_DONE")
    }
}