package com.example.task_planner.presentation.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
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
import com.example.task_planner.presentation.screens.tab_page_screen.TabPageScreenViewModel
import com.example.task_planner.ui.theme.Typography
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskScreenList(list: List<TaskWorker>, tabPageScreenViewModel: TabPageScreenViewModel){
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        LazyColumn(modifier = Modifier.fillMaxSize()){
            itemsIndexed(list){ index, task ->
                TaskElement(task = task, index = index, tabPageScreenViewModel)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("SimpleDateFormat")
@Composable
fun TaskElement(task: TaskWorker?, index: Int, tabPageScreenViewModel: TabPageScreenViewModel){
    Card(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
        backgroundColor = Color.White,
        elevation = 8.dp
    ) {
        val colorStatus = when{
            task?.IsDone == 1 && ConvertToDate(task.DateDone.toString())?.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDate()!! <= LocalDate.now() -> Color.Red
            else -> Color.Black;
        }
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)){
            Text(text = task?.NameTask.toString(), style = Typography.titleMedium, color = colorStatus)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = task?.TaskOverview.toString(), style = Typography.bodySmall, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Срок выполнения: ", style = Typography.bodySmall, color = colorStatus)
            Text(text = "до " + task?.DateDone.toString(), style = Typography.bodySmall, color = colorStatus)
            Spacer(modifier = Modifier.height(8.dp))
            ButtonElement(task?.IsDone!!.toInt()) {
                val updatedTask = task.copy(CompletionDate = SimpleDateFormat("dd.MM.yyyy").format(Date()), IsDone = 2)
                val user = Firebase.auth.currentUser?.uid.toString()
                tabPageScreenViewModel.updateTask(user, index, updatedTask)
            }
        }
    }
}