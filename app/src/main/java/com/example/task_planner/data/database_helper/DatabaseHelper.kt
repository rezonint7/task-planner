package com.example.task_planner.data.database_helper

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class DatabaseHelper {
    companion object{
        fun getAuthDatabase() = FirebaseAuth.getInstance()
        fun getRealTimeDatabase() = FirebaseDatabase.getInstance()
    }
}