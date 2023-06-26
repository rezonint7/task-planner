package com.example.task_planner.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun TaskScreenList(){
    val user = remember { FirebaseAuth.getInstance().currentUser }
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "TASK")
    }
}

@Composable
fun TaskElement(){

}