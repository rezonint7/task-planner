package com.example.task_planner.data.models

data class TaskWorker(
    val DateDone: String? = "",
    val CompletionDate: String? = "",
    val IsDone: Int = -1,
    val NameTask: String? = "",
    val TaskOverview: String? = "",
    val UUID: String? = ""
)