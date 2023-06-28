package com.example.task_planner.presentation.screens.tab_page_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.task_planner.presentation.screens.TaskDoneScreenList
import com.example.task_planner.presentation.screens.TaskScreenList

@Composable
fun TabPage(
    controller: NavController,
    tabPageScreenViewModel: TabPageScreenViewModel = hiltViewModel()
){
    var tabIndex by remember { mutableStateOf(0) }

    val tabs = listOf("текущие", "выполненные")

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex, backgroundColor = Color.Blue, contentColor = Color.White) {
            tabs.forEachIndexed { index, title ->
                Tab(text = { Text(title) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index }
                )
            }
        }
        val tasks = tabPageScreenViewModel.tasks.value.tasks
        when (tabIndex) {
            0 -> TaskScreenList(tasks ?: emptyList())
            1 -> TaskDoneScreenList()
        }
    }
}

@Preview
@Composable
fun preview(){
    TabPage(controller = rememberNavController())
}