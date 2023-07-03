package com.example.task_planner.presentation.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.task_planner.data.models.TaskWorker
import com.example.task_planner.presentation.screens.main_screen.ButtonElement
import com.example.task_planner.ui.theme.Typography
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskDoneScreenList(list: List<TaskWorker>){
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        LazyColumn(modifier = Modifier.fillMaxSize()){
            items(list){ task ->
                TaskDoneElement(task = task)
            }
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskDoneElement(task: TaskWorker?){
    Card(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
        backgroundColor = Color(0xFF86DDFF),
        elevation = 8.dp
    ) {
        val statusText = when{
            (task?.IsDone == 2 && ConvertToDate(task.DateDone.toString())?.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDate()!! <= LocalDate.now()) -> "Выполнена в срок"
            else -> "Выполнено с задержкой"
        }
        val colorStatusText = if(statusText == "Выполнено с задержкой") Color.Red else Color.Green
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)){
            Text(text = task?.NameTask.toString(), style = Typography.titleMedium, color = colorStatusText)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = statusText, style = Typography.bodySmall, color = colorStatusText)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = task?.TaskOverview.toString(), style = Typography.bodySmall, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Срок выполнения: ", style = Typography.bodySmall, color = Color.Black)
            Text(text = "до " + task?.DateDone.toString(), style = Typography.bodySmall, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Дата выполнения: ", style = Typography.bodySmall, color = Color.Black)
            Text(text = task?.CompletionDate.toString(), style = Typography.bodySmall, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@SuppressLint("SimpleDateFormat")
fun ConvertToDate(date: String): Date? = SimpleDateFormat("dd.MM.yyyy").parse(date)