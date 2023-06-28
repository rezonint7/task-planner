package com.example.task_planner.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.task_planner.data.models.TaskWorker

@Composable
fun TaskDoneScreenList(list: List<TaskWorker>){
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        LazyColumn(modifier = Modifier.fillMaxSize()){
            items(list){ task ->
                TaskElement(task = task)
            }
        }
    }
}