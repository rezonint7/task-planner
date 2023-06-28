package com.example.task_planner.data.repository

import android.util.Log
import com.example.task_planner.common.Constants
import com.example.task_planner.data.database_helper.DatabaseHelper
import com.example.task_planner.data.models.TaskWorker
import com.example.task_planner.data.models.Worker
import com.example.task_planner.domain.repository.DatabaseService
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.internal.resumeCancellableWith
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class DatabaseServiceImpl: DatabaseService {
    override suspend fun getAuthUser() = DatabaseHelper.getAuthDatabase().currentUser

    override suspend fun authorizeUser(email: String, password: String): Task<AuthResult> {
        return DatabaseHelper.getAuthDatabase().signInWithEmailAndPassword(email, password)
    }

    override suspend fun getTasksList(userKey: String): List<TaskWorker> = suspendCoroutine { continuation ->
        val database = DatabaseHelper.getRealTimeDatabase().getReferenceFromUrl(Constants.DATABASE_NAME).child(userKey)
        Log.d("333", database.key.toString())
        val taskListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val person = snapshot.getValue(Worker::class.java)
                val tasks = person?.TaskWorker ?: emptyList()
                Log.d("333", person.toString())
                Log.d("333", tasks.toString())
                Log.d("333", userKey)
                continuation.resumeWith(Result.success(tasks))
            }

            override fun onCancelled(error: DatabaseError) {
                continuation.resumeWithException(error.toException())
            }
        }
        database.addValueEventListener(taskListener)

//        continuation.resumeCancellableWith()  {
//            database.removeEventListener(taskListener)
//        }
    }
}