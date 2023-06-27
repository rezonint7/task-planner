package com.example.task_planner.domain.use_cases

import android.util.Log
import com.example.task_planner.common.Resource
import com.example.task_planner.domain.repository.DatabaseService
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.logging.Handler
import javax.inject.Inject

class AuthUserUseCase @Inject constructor(private val repository: DatabaseService){
    operator fun invoke(login: String, password: String): Flow<Resource<FirebaseUser?>> = flow {
        try{
            emit(Resource.Loading())
            val result = repository.authorizeUser(login, password).await().user
            Log.d("error", result?.email.toString())
            emit(Resource.Success(result))

        }catch (e: FirebaseAuthException){
            emit(Resource.Error(message = e.message.toString()))
        }
    }
}