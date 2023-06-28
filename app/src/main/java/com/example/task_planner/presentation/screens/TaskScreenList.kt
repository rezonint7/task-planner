package com.example.task_planner.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.task_planner.data.database_helper.DatabaseHelper
import com.example.task_planner.data.models.TaskWorker
import com.example.task_planner.presentation.screens.main_screen.ButtonElement
import com.example.task_planner.ui.theme.Typography
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

@Composable
fun TaskScreenList(list: List<TaskWorker>){
    val user = remember { FirebaseAuth.getInstance().currentUser }
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        LazyColumn(modifier = Modifier.fillMaxSize()){
            items(list.drop(1)){ task ->
                TaskElement(task = task)
            }
        }
    }
}

@Composable
fun TaskElement(task: TaskWorker?){
    Card(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)){
            Text(text = task?.NameTask.toString(), style = Typography.titleMedium, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = task?.TaskOverview.toString(), style = Typography.titleMedium, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Срок выполнения: ", style = Typography.titleMedium, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "до " + task?.DateDone.toString(), style = Typography.titleMedium, color = Color.Black)
        }
        ButtonElement("Отметить как выполненную") {

        }
    }
}

@Preview
@Composable
fun preview1() {

}