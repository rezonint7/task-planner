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
        var user: FirebaseUser? = null
        try{
            emit(Resource.Loading())
            val task = repository.authorizeUser(login, password).addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    user = Firebase.auth.currentUser
                    Log.d("EMAIL2", user?.email.toString())

                }
            }.addOnFailureListener { Log.d("ERROR", it.message.toString()) }.await().user
            val result = repository.authorizeUser(login, password).await()


            emit( Resource.Success(result.user))

        }catch (e: FirebaseAuthException){
            emit(Resource.Error(message = e.message.toString()))
        }
    }
}