package com.example.task_planner.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.task_planner.R
import com.example.task_planner.presentation.navigation.NavigationScreens
import com.example.task_planner.ui.theme.TASK_PLANNERTheme
import com.example.task_planner.ui.theme.Typography

@Composable
fun MainScreen(controller: NavController){
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
        Spacer(modifier = Modifier.height(118.dp))
        ButtonElement(text = "Войти в систему") {
            if(login.value.isNotBlank() && password.value.isNotBlank()){
                controller.navigate(NavigationScreens.TabPage.route)
            }
        }
    }
}

@Composable
fun ButtonElement(text: String = "", onClick: () -> Unit){
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A9EC))
    ) {
        Text(text = text, style = Typography.titleMedium)
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