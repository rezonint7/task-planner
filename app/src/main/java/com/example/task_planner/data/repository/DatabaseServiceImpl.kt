package com.example.task_planner.data.repository

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.internal.resumeCancellableWith
import kotlinx.coroutines.suspendCancellableCoroutine
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date
import java.util.Objects
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
    override suspend fun updateTask(userKey: String, index: Int, updatedTask: TaskWorker): Boolean {
        val database = DatabaseHelper.getRealTimeDatabase().getReferenceFromUrl(Constants.DATABASE_NAME).child(userKey).child("TaskWorker")
        return suspendCancellableCoroutine { continuation ->
            val taskListener = object : ValueEventListener {
                @RequiresApi(Build.VERSION_CODES.TIRAMISU)
                @SuppressLint("SimpleDateFormat")
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.d("123", snapshot.toString())
                    val tasks = snapshot.getValue<Map<String, TaskWorker>>()
                    if (index >= 0 && index < tasks?.size!!) {
//                        tasks[index] = updatedTask
                        val post = hashMapOf("TaskWorker" to tasks).toMap()
                        database.updateChildren(post).addOnSuccessListener {
                            continuation.resume(true)
                        }.addOnFailureListener { error ->
                            continuation.resumeWithException(error)
                        }
                    }
                    else {
                        continuation.resumeWithException(IndexOutOfBoundsException("Index out of bounds"))
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