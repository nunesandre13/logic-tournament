package com.example.app.view.userViews

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.app.viewModel.UserStateUI
import com.example.app.viewModel.UserViewModel
import dto.LogInUserDTO

@Composable
fun LoginView(
    viewModel: UserViewModel,
    onLoginSuccess: () -> Unit,
    onNavigateToCreateUser: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val userState by viewModel.userState.collectAsState()

    // Reage ao estado de login do ViewModel
    LaunchedEffect(userState) {
        if (userState is UserStateUI.LoggedIn) {
            onLoginSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login to your account", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.login(LogInUserDTO(email, password))
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = email.isNotBlank() && password.isNotBlank() // Botão ativado apenas com campos preenchidos
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(8.dp))
        TextButton(onClick = onNavigateToCreateUser) {
            Text("Create a new account")
        }

        // Exibir o estado de erro, se houver
        if (userState is UserStateUI.Error) {
            Text(
                text = (userState as UserStateUI.Error).message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}