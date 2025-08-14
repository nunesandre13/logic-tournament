package com.example.app.view.userViews

sealed class AuthScreenState {
    object Login : AuthScreenState()
    object CreateAccount : AuthScreenState()
}