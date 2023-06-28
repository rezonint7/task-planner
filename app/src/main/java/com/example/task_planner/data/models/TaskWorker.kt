package com.example.task_planner.data.models


import com.google.gson.annotations.SerializedName

data class TaskWorker(
    val DateDone: String? = "",
    val IsDone: Int = -1,
    val NameTask: String? = "",
    val TaskOverview: String? = ""
)