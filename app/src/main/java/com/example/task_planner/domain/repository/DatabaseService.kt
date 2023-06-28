package com.example.task_planner.domain.repository

import com.example.task_planner.data.models.TaskWorker
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser

interface DatabaseService {
    suspend fun getAuthUser(): FirebaseUser?
    suspend fun authorizeUser(email: String, password: String): Task<AuthResult>
    suspend fun getTasksList(userKey: String): List<TaskWorker>
}