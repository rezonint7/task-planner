package com.example.task_planner.data.repository

import com.example.task_planner.data.database_helper.DatabaseHelper
import com.example.task_planner.data.remote.DatabaseService
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import javax.inject.Inject

class DatabaseServiceImpl: DatabaseService {
    override suspend fun getAuthUser() = DatabaseHelper.getAuthDatabase().currentUser

    override suspend fun authorizeUser(email: String, password: String): Task<AuthResult> {
        return DatabaseHelper.getAuthDatabase().signInWithEmailAndPassword(email, password)
    }

    override suspend fun getTasksList() {

    }
}