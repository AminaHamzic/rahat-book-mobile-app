package com.example.moodapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.moodapp.R
import com.example.moodapp.Screen
import com.example.moodapp.UserViewModel
import kotlinx.coroutines.launch




@Composable
fun RegisterScreen(navController: NavHostController, viewModel: UserViewModel = viewModel()) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }

    val drawableId = R.drawable.colorful_cute_fun_world_logo__2_
    val painter = painterResource(drawableId)


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            Image(
                painter = painter,
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Username:",
                modifier = Modifier.align(Alignment.Start),
                color = Color.Magenta
            )

            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Password:",
                modifier = Modifier.align(Alignment.Start),
                color = Color.Magenta
            )

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Confirm Password:",
                modifier = Modifier.align(Alignment.Start),
                color = Color.Magenta
            )

            TextField (
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier.width(150.dp),
                colors = ButtonDefaults.buttonColors(Color.Magenta),

                onClick = {
                    if (username.isBlank() || password.isBlank()) {
                        viewModel.viewModelScope.launch {
                            snackbarHostState.showSnackbar("Username or password cannot be empty")
                        }
                    } else if (confirmPassword != password ) {
                        viewModel.viewModelScope.launch {
                            snackbarHostState.showSnackbar("Passwords do not match")
                        }
                    } else {
                        viewModel.viewModelScope.launch {
                            try {
                                viewModel.register(username, password)
                                navController.navigate(Screen.LoginScreen.route)
                            } catch (e: Exception) {
                                snackbarHostState.showSnackbar("Registration failed: ${e.message}")
                            }
                        }
                    }
                }
            ) {
                Text("Register")
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = { navController.navigate(Screen.LoginScreen.route) }
            ) {
                Text("LogIn")
            }
        }
    }
}