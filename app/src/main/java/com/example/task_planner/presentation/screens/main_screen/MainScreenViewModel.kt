package com.example.task_planner.presentation.screens.main_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task_planner.common.Resource
import com.example.task_planner.domain.repository.DatabaseService
import com.example.task_planner.domain.use_cases.AuthUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(private val authUserUseCase: AuthUserUseCase): ViewModel() {
    private val _userInfo = mutableStateOf<MainScreenState>(MainScreenState())
    val userInfo: State<MainScreenState> = _userInfo

    fun AuthorizeUser(login: String, password: String){
        authUserUseCase(login, password).onEach { result ->
            when(result){
                is Resource.Error -> {
                    _userInfo.value = MainScreenState(error = result.message.toString())
                }
                is Resource.Loading -> {
                    _userInfo.value = MainScreenState(idLoading = true)
                }
                is Resource.Success -> {
                    _userInfo.value = MainScreenState(user = result.data)
                    Log.d("Email", _userInfo.value.user?.email.toString())
                }
            }
        }.launchIn(viewModelScope)
    }
}