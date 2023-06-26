package com.example.task_planner.presentation.screens

import com.example.task_planner.data.remote.DatabaseService
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(private val databaseService: DatabaseService) {

    fun AuthorizeUser(email: String, password: String){
        databaseService.authorizeUser(email, password)
    }
}