package com.example.task_planner.presentation.screens.tab_page_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.task_planner.presentation.screens.TaskDoneScreenList
import com.example.task_planner.presentation.screens.TaskScreenList
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabPage(
    controller: NavController,
    tabPageScreenViewModel: TabPageScreenViewModel = hiltViewModel()
) {
    val tabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("текущие", "выполненные")
    val pagerState = rememberPagerState(initialPage = tabIndex, pageCount = tabs.size)
    val coroutineScope = rememberCoroutineScope()
    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(
            selectedTabIndex = tabIndex,
            backgroundColor = Color.White,
            contentColor = Color(0xFF00A9EC),
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(
                        pagerState,
                        tabPositions
                    )
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = tabIndex == index,
                    onClick = { coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }},
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f),
            dragEnabled = true
        ) { page ->
            when (page) {
                0 -> TaskScreenList(tabPageScreenViewModel.tasks.value.tasks?.drop(1)?.filter { task -> task.IsDone == 1 } ?: emptyList(), tabPageScreenViewModel)
                1 -> TaskDoneScreenList(tabPageScreenViewModel.tasks.value.tasks?.drop(1)?.filter { task -> task.IsDone == 3 } ?: emptyList())
            }
        }
    }
}