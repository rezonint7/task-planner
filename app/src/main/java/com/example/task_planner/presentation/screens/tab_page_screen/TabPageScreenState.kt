package com.example.task_planner.presentation.screens.tab_page_screen

import com.example.task_planner.data.models.TaskWorker
import com.google.firebase.auth.FirebaseUser

data class TabPageScreenState(
    val isLoading: Boolean = false,
    val tasks: List<TaskWorker>? = null,
    val error: String = ""
)
data class UpdatedTask(
    val isUpdated: Boolean = false,
    val error: String = ""
)

data class ErrorResult(val errorCode: Int, val errorMessage: String)