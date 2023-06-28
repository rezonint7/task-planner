package com.example.task_planner.presentation.screens.main_screen

import com.google.firebase.auth.FirebaseUser

data class MainScreenState(
    val isLoading: Boolean = false,
    val user: FirebaseUser? = null,
    val error: String = ""
)
