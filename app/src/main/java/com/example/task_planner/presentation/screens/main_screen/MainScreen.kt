package com.example.task_planner.presentation.screens.main_screen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.task_planner.R
import com.example.task_planner.presentation.navigation.NavigationScreens
import com.example.task_planner.ui.theme.Typography

@Composable
fun MainScreen(
    controller: NavController,
    mainScreenViewModel: MainScreenViewModel = hiltViewModel()
){
    val login = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_task),
            contentDescription = "logo",
            modifier = Modifier
                .size(272.dp)
                .padding(top = 18.dp)
        )
        Text(text = stringResource(id = R.string.task_planner), style = Typography.titleLarge)
        Spacer(modifier = Modifier.height(78.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.email),
                style = Typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            )
            EditTextElement(hint = "Введите логин", login)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.password),
                style = Typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            )
            EditTextElement(hint = "Введите пароль", password, true)
        }
        Spacer(modifier = Modifier.height(108.dp))
        ButtonElement {
            Log.d("EMAIL", mainScreenViewModel.userInfo.value.user?.email.toString())
            if(login.value.isNotBlank() && password.value.isNotBlank()){
                mainScreenViewModel.AuthorizeUser(login.value.trim(), password.value.trim())
            }
        }
        var navigateToTabPage by remember { mutableStateOf(false) }
        LaunchedEffect(mainScreenViewModel.userInfo.value.user?.email) {
            if (mainScreenViewModel.userInfo.value.user?.email?.isNotBlank() == true && !navigateToTabPage) {
                navigateToTabPage = true
                controller.navigate(NavigationScreens.TabPage.route){
                    popUpTo(NavigationScreens.Main.route) {
                        inclusive = true
                    }
                }
            }
        }
    }
}

@Composable
fun ButtonElement(isDone: Int = -1, onClick: () -> Unit){
    val text = when(isDone){
        1 -> "Отметить как выполненную"
        2 -> "Выполнено"
        3 -> "123"
        else -> "Войти в систему"
    }

    Button(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth().height(48.dp)
            .padding(horizontal = 14.dp)
            .border(border = BorderStroke(1.dp, Color(0xFF00A9EC)), shape = RoundedCornerShape(10.dp)),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A9EC), disabledContainerColor = Color(0xFF86DDFF)),
        enabled = isDone in -1..1
    ) {
        if(isDone == 2 or 3) Icon(
            imageVector = Icons.Default.Done,
            contentDescription = "iconDone",
            modifier = Modifier.size(48.dp),
            tint = Color(0xA9000000)
        )
        val color = if(isDone == 2) Color(0xA9000000) else Color.White
        Text(text = text, style = Typography.titleMedium, color = color)
    }
}

@Composable
fun EditTextElement(hint: String, text: MutableState<String> = mutableStateOf(""), isPassword: Boolean = false){
    var value by remember {text}
    BasicTextField(
        value = value,
        onValueChange = { newText ->
            value = newText
            text.value = newText
        },
        textStyle = Typography.bodySmall,
        visualTransformation = if(isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = if(isPassword) KeyboardOptions(keyboardType = KeyboardType.Password) else KeyboardOptions(keyboardType = KeyboardType.Text),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .padding(horizontal = 14.dp) // margin left and right
                    .fillMaxWidth()
                    .background(Color(0xFF86DDFF), shape = RoundedCornerShape(size = 5.dp))
                    .padding(horizontal = 8.dp, vertical = 8.dp),
            ) {
                if (value.isEmpty()) {
                    Text(
                        text = hint,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray,
                    )
                }
                innerTextField()
            }
        }
    )
}