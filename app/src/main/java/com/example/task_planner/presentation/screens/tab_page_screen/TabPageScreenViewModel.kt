package com.example.task_planner.presentation.screens.tab_page_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task_planner.common.Resource
import com.example.task_planner.data.models.TaskWorker
import com.example.task_planner.data.sharedprefs_helper.SharedPrefsHelper
import com.example.task_planner.domain.repository.DatabaseService
import com.example.task_planner.domain.use_cases.AuthUserUseCase
import com.example.task_planner.domain.use_cases.GetDataUseCase
import com.example.task_planner.domain.use_cases.UpdateDataUseCase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TabPageScreenViewModel @Inject constructor(
    private val getDataUseCase: GetDataUseCase,
    private val updateDataUseCase: UpdateDataUseCase,
    private val sharedPrefsHelper: SharedPrefsHelper): ViewModel() {
    private val _tasks = mutableStateOf<TabPageScreenState>(TabPageScreenState())
    private val _isUpdatedTask = mutableStateOf<UpdatedTask>(UpdatedTask())
    val tasks: State<TabPageScreenState> = _tasks
    val isUpdatedTask = _isUpdatedTask

    init {
        getTasks(Firebase.auth.currentUser?.uid ?: sharedPrefsHelper.getToken().toString())
    }

    private fun getTasks(userKey: String){
        getDataUseCase(userKey).onEach { result ->
            when(result){
                is Resource.Error -> {
                    _tasks.value = TabPageScreenState(error = result.message.toString())
                }
                is Resource.Loading -> {
                    _tasks.value = TabPageScreenState(isLoading = true)
                }
                is Resource.Success -> {
                    _tasks.value = TabPageScreenState(tasks = result.data)
                }
            }
            Log.d("error", "vm")
        }.launchIn(viewModelScope)
    }

    fun updateTask(userKey: String, UUID: String, updatedTask: TaskWorker){
        updateDataUseCase(userKey, UUID, updatedTask).onEach { result ->
            when(result){
                is Resource.Error -> { _isUpdatedTask.value = UpdatedTask(error = result.message ?: "") }
                is Resource.Success -> { _isUpdatedTask.value = UpdatedTask(isUpdated = result.data ?: false) }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }
}