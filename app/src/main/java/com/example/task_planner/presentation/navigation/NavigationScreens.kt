package com.example.task_planner.presentation.navigation

sealed class NavigationScreens(val route: String){
    object Main: NavigationScreens("main_screen")
    object TaskList: NavigationScreens("task_list_screen")
    object TaskDoneList: NavigationScreens("task_done_list_screen")
    object TabPage: NavigationScreens("tab_page")
}
