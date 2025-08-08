package com.example.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeuApp()
        }
    }
}

@Composable
fun MeuApp() {
    MaterialTheme {
        Surface {
            Greeting("Bem-vindo ao seu app!")
        }
        LoginScreen()
    }
}

@Composable
fun Greeting(text: String) {
    Text(text = text)
}


@Composable
fun LoginScreen() {
    // Definimos variáveis de estado para armazenar o email e a senha.
    // O 'by remember' garante que o estado é mantido nas recomposições.
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginMessage by remember { mutableStateOf("") }

    // Usamos um Surface para definir o fundo da nossa tela.
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        // Um Column para organizar os elementos verticalmente no centro.
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título da tela.
            Text(
                text = "Bem-vindo!",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Campo de texto para o email.
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de texto para a senha.
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Senha") },
                // O PasswordVisualTransformation esconde a senha com pontos.
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botão de login.
            Button(
                onClick = {
                    // Lógica para o login
                    if (email.isNotBlank() && password.isNotBlank()) {
                        loginMessage = "Login bem-sucedido!"
                        // Aqui você pode adicionar a sua lógica de navegação.
                        // Por exemplo, navegar para a tela principal.
                    } else {
                        loginMessage = "Por favor, preencha todos os campos."
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Exibe uma mensagem de status (sucesso ou erro).
            Text(
                text = loginMessage,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

// A função de pré-visualização para ver como a tela ficará.
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}
