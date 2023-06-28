package com.example.task_planner.presentation.screens.tab_page_screen

import com.example.task_planner.data.models.TaskWorker
import com.google.firebase.auth.FirebaseUser

data class TabPageScreenState(
    val isLoading: Boolean = false,
    val tasks: List<TaskWorker>? = null,
    val error: String = ""
)