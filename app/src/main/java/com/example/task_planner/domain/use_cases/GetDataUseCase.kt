package com.example.task_planner.domain.use_cases

import android.util.Log
import com.example.task_planner.common.Resource
import com.example.task_planner.data.models.TaskWorker
import com.example.task_planner.domain.repository.DatabaseService
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDataUseCase @Inject constructor(private val repository: DatabaseService) {
    operator fun invoke(userKey: String): Flow<Resource<List<TaskWorker>?>> = flow {
        try{
            emit(Resource.Loading())
            repository.getTasksList(userKey).collect { tasks ->
                emit(Resource.Success(tasks))
            }
        }catch(e: FirebaseTooManyRequestsException){

        }
    }
}