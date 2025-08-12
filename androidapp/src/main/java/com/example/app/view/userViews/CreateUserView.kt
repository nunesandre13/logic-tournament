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
import dto.UserCreationDTO


@Composable
fun CreateUserView(
    viewModel: UserViewModel,
    onUserCreated: () -> Unit, // Callback para navegar após a criação bem-sucedida
    onNavigateToLogin: () -> Unit // Callback para navegar para o login
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val userState by viewModel.userState.collectAsState()

    // Se o estado mudar para LoggedIn, navegue de volta ou mostre uma mensagem de sucesso
    LaunchedEffect(userState) {
        if (userState is UserStateUI.LoggedIn) {
            onUserCreated()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Create a New Account", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

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
                val newUser = UserCreationDTO(username, email, password)
                viewModel.createUser(newUser)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create Account")
        }

        // Novo TextButton para navegar para o login
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(onClick = onNavigateToLogin) {
            Text("Already have an account? Login")
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