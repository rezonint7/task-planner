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
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.internal.resumeCancellableWith
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class DatabaseServiceImpl: DatabaseService {
    override suspend fun getAuthUser() = DatabaseHelper.getAuthDatabase().currentUser

    override suspend fun authorizeUser(email: String, password: String): Task<AuthResult> {
        return DatabaseHelper.getAuthDatabase().signInWithEmailAndPassword(email, password)
    }

    override suspend fun getTasksList(userKey: String): Flow<List<TaskWorker>> = callbackFlow {
        val database = DatabaseHelper.getRealTimeDatabase().getReferenceFromUrl(Constants.DATABASE_NAME).child(userKey)
        val taskListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tasks = snapshot.getValue(Worker::class.java)?.TaskWorker ?: emptyList()
                trySend(tasks)
            }

            override fun onCancelled(error: DatabaseError) {
                cancel("Cancellation message", error.toException())
            }
        }
        database.addValueEventListener(taskListener)
        awaitClose {
            database.removeEventListener(taskListener)
        }
    }

    override suspend fun updateTask(userKey: String, updatedTasks: List<TaskWorker>) {
        val database = DatabaseHelper.getRealTimeDatabase().getReferenceFromUrl(Constants.DATABASE_NAME).child(userKey)
        return suspendCancellableCoroutine { continuation ->
            val taskListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val person = snapshot.getValue(Worker::class.java)
                    val tasks = person?.TaskWorker ?: emptyList()
                    if (tasks != updatedTasks) {
                        database.setValue(updatedTasks)
                            .addOnSuccessListener {
                                continuation.resume(Unit)
                            }
                            .addOnFailureListener { exception ->
                                continuation.resumeWithException(exception)
                            }
                    } else {
                        continuation.resume(Unit)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    continuation.resumeWithException(error.toException())
                }
            }
            database.addListenerForSingleValueEvent(taskListener)
            continuation.invokeOnCancellation {
                database.removeEventListener(taskListener)
            }
        }
    }
}