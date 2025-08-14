package com.example.app.view.userViews

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


@Composable
fun AuthScreen(
    onLogin: (String, String) -> Unit,
    onCreateAccount: (String, String, String) -> Unit
) {

    var currentScreen by remember { mutableStateOf<AuthScreenState>(AuthScreenState.Login) }

    when (currentScreen) {
        is AuthScreenState.Login -> {
            LoginScreen(
                onLogin = onLogin,
                onNavigateToCreateAccount = {
                    currentScreen = AuthScreenState.CreateAccount
                }
            )
        }
        is AuthScreenState.CreateAccount -> {
            CreateAccountScreen(
                onCreateAccount = onCreateAccount,
                onNavigateToLogin = {
                    currentScreen = AuthScreenState.Login
                }
            )
        }
    }
}

