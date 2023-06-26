package com.example.task_planner.data.remote

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser

interface DatabaseService {
    suspend fun getAuthUser(): FirebaseUser?
    suspend fun authorizeUser(email: String, password: String): Task<AuthResult>
    suspend fun getTasksList()
}