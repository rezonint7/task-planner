package com.example.task_planner.data.models

import com.google.gson.annotations.SerializedName

data class Worker(
    val Name: String = "",
    val TaskWorker: List<TaskWorker>? = emptyList()
)
