package com.example.task_planner.domain.use_cases

import android.util.Log
import com.example.task_planner.common.Resource
import com.example.task_planner.data.models.TaskWorker
import com.example.task_planner.domain.repository.DatabaseService
import com.example.task_planner.presentation.screens.tab_page_screen.UpdatedTask
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateDataUseCase @Inject constructor(private val repository: DatabaseService) {
    operator fun invoke(userKey: String, UUID: String, updatedTask: TaskWorker): Flow<Resource<Boolean>> = flow {
        try{
            emit(Resource.Loading())
            val result = repository.updateTask(userKey, UUID, updatedTask)
            emit(Resource.Success(result))

        }catch(e: FirebaseTooManyRequestsException){

        }
    }
}